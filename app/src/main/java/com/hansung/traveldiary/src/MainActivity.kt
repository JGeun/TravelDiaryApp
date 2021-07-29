package com.hansung.traveldiary.src

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.src.home.HomeFragment
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMainBinding
import com.hansung.traveldiary.src.bulletin.BulletinFragment
import com.hansung.traveldiary.src.plan.model.PlanTotalData
import com.hansung.traveldiary.src.profile.ProfileFragment
import com.hansung.traveldiary.src.travel.AddTravelPlanActivity
import com.hansung.traveldiary.src.travel.TravelBaseFragment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class PlanBookData(var title: String, var planTotalData: PlanTotalData)

class MainActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    private lateinit var addNewPlanBookTask : ActivityResultLauncher<Intent>

    private val TAG = "MainActivity"

    companion object{
        val planBookList = ArrayList<PlanBookData>()
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        val dbCollection = db!!.collection(user!!.email.toString())
        dbCollection
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val docRef = dbCollection.document("plan").collection("data").document(document.id)
                        .get().addOnSuccessListener { documentSnapshot ->
                            val data = documentSnapshot.toObject<PlanTotalData>()!!
//                            println("data: $data")
                            planBookList.add(PlanBookData(document.id, data))

                        }.addOnFailureListener { exception ->
                            Log.d(TAG, "Error getting documents: ", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        val pref = applicationContext.getSharedPreferences("login", 0)
        Log.d("MainActivity", pref.getString("login", "")!!)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.main_btm_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "home")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_bulletin -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, BulletinFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "bulletin")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_travel -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "travel")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_profile -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ProfileFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "profile")
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })

        addNewPlanBookTask = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            supportFragmentManager.beginTransaction().replace(R.id.main_frm, TravelBaseFragment()).commitAllowingStateLoss()
        }
    }


    fun makePlanBook(){
        addNewPlanBookTask.launch(Intent(this@MainActivity, AddTravelPlanActivity::class.java))
    }
}


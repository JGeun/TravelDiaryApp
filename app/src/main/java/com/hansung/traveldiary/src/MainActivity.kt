package com.hansung.traveldiary.src

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.hansung.traveldiary.src.plan.diary
import com.hansung.traveldiary.src.profile.ProfileFragment
import com.hansung.traveldiary.src.travel.AddBook.AddTravelPlanActivity
import com.hansung.traveldiary.src.travel.TravelBaseFragment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    private lateinit var addNewPlanBookTask: ActivityResultLauncher<Intent>
    private val TAG = "MainActivity"
    private val userList = UserEmailList()

    companion object {
        var diaryTitleList = TitleList()
        var planTitleList = TitleList()
        val planBookList = ArrayList<PlanBookData>()
        val myDiaryList = ArrayList<DiaryBulletinData>()
        val allDiaryList = ArrayList<DiaryBulletinData>()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        getDBData()

        val pref = applicationContext.getSharedPreferences("login", 0)
        Log.d("MainActivity", pref.getString("login", "")!!)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

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
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                println("resultcode 들어옴")
                updatePlanBookList(result.data?.getStringExtra("title").toString(), "add")

            }
        }
    }

    fun makePlanBook() {
        addNewPlanBookTask.launch(Intent(this@MainActivity, AddTravelPlanActivity::class.java))
    }

    fun getDBData() {
        getPlanData()
        getAllDiaryData()
    }

    fun getPlanData() {
        val userDocRef = db!!.collection("User").document("UserData")
        //getTitle
        userDocRef.collection(user!!.email.toString()).document("Plan")
            .get()
            .addOnSuccessListener { result ->
                val data = result.data?.get("titleFolder")
                if (data != null) {
                    planTitleList.titleFolder = data as ArrayList<String>
                    println("size: ${planTitleList.titleFolder.size}")
                    println("content: ${planTitleList.titleFolder[0]}")
                }
                //get Data about Title
                getPlanAllData()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getPlanAllData() {
        for (title in planTitleList.titleFolder) {
            updatePlanBookList(title)
        }
    }

    fun updatePlanBookList(title: String, check: String = "default") {
        val userDocRef = db!!.collection("User").document("UserData")
        val planDocRef = userDocRef.collection(user!!.email.toString()).document("Plan")
        var planBaseData: PlanBaseData? = null
        var placeInfoFolder: PlaceInfoFolder? = null
        planDocRef.collection(title).document("BaseData")
            .get()
            .addOnSuccessListener { result ->
                planBaseData = result.toObject<PlanBaseData>()
                if (planBaseData != null && placeInfoFolder != null) {
                    planBookList.add(
                        PlanBookData(
                            title,
                            PlanData(planBaseData!!, placeInfoFolder!!)
                        )
                    )
                    if (check == "add") {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        planDocRef.collection(title).document("PlaceInfo")
            .get()
            .addOnSuccessListener { result ->
                placeInfoFolder = result.toObject<PlaceInfoFolder>()
                if (planBaseData != null && placeInfoFolder != null) {
                    planBookList.add(
                        PlanBookData(
                            title,
                            PlanData(planBaseData!!, placeInfoFolder!!)
                        )
                    )
                    if (check == "add") {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


    fun getAllDiaryData() {
        //getTitle
        println("getAllDiaryData")
        val myEmail = user!!.email.toString()
        val userDocRef = db!!.collection("User").document("UserData")
        userDocRef.get()
            .addOnSuccessListener { result ->
                println("emailFolder")
                val data = result.data?.get("emailFolder")
                if (data != null) {
                    userList.emailFolder = data as ArrayList<String>

                    for (email in userList.emailFolder) {
                        userDocRef.collection(email).document("Diary")
                            .get()
                            .addOnSuccessListener { result ->
                                println("titleFolder")
                                val data = result.data?.get("titleFolder")
                                if (data != null) {
                                    diaryTitleList.titleFolder = data as ArrayList<String>
                                    for (title in diaryTitleList.titleFolder) {
                                        if (myEmail == email)
                                            getUserDiaryData(email, title, true)
                                        else
                                            getUserDiaryData(email, title, false)
                                    }
                                }
                            }
                    }
                }

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


    fun getUserDiaryData(email: String, title: String, isMyEmail: Boolean) {
        println("getUserDiaryData")
        val userDocRef = db!!.collection("User").document("UserData")
        val diaryDocRef = userDocRef.collection(email).document("Diary")

        var planBaseData: PlanBaseData? = null
        var placeInfoFolder: PlaceInfoFolder? = null
        var diaryInfoFolder: DiaryInfoFolder? = null
        var diaryBaseData: DiaryBaseData? = null

        diaryDocRef.collection(title).document("PlanBaseData")
            .get()
            .addOnSuccessListener { result ->
                planBaseData = result.toObject<PlanBaseData>()
                if (planBaseData != null && placeInfoFolder != null && diaryInfoFolder != null && diaryBaseData != null) {
                    val diaryBulletinData = DiaryBulletinData(
                        title,
                        DiaryData(
                            planBaseData!!,
                            diaryBaseData!!,
                            placeInfoFolder!!,
                            diaryInfoFolder!!
                        )
                    )
                    allDiaryList.add(diaryBulletinData)
                    if (isMyEmail) {
                        myDiaryList.add(diaryBulletinData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        diaryDocRef.collection(title).document("PlanPlaceInfo")
            .get()
            .addOnSuccessListener { result ->
                placeInfoFolder = result.toObject<PlaceInfoFolder>()
                if (planBaseData != null && placeInfoFolder != null && diaryInfoFolder != null && diaryBaseData != null) {
                    val diaryBulletinData = DiaryBulletinData(
                        title,
                        DiaryData(
                            planBaseData!!,
                            diaryBaseData!!,
                            placeInfoFolder!!,
                            diaryInfoFolder!!
                        )
                    )
                    allDiaryList.add(diaryBulletinData)
                    if (isMyEmail) {
                        myDiaryList.add(diaryBulletinData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        diaryDocRef.collection(title).document("DiaryBaseData")
            .get()
            .addOnSuccessListener { result ->
                diaryBaseData = result.toObject<DiaryBaseData>()
                if (planBaseData != null && placeInfoFolder != null && diaryInfoFolder != null && diaryBaseData != null) {
                    val diaryBulletinData = DiaryBulletinData(
                        title,
                        DiaryData(
                            planBaseData!!,
                            diaryBaseData!!,
                            placeInfoFolder!!,
                            diaryInfoFolder!!
                        )
                    )
                    allDiaryList.add(diaryBulletinData)
                    if (isMyEmail) {
                        myDiaryList.add(diaryBulletinData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        diaryDocRef.collection(title).document("DiaryData")
            .get()
            .addOnSuccessListener { result ->
                diaryInfoFolder = result.toObject<DiaryInfoFolder>()
                if (planBaseData != null && placeInfoFolder != null && diaryInfoFolder != null && diaryBaseData != null) {
                    val diaryBulletinData = DiaryBulletinData(
                        title,
                        DiaryData(
                            planBaseData!!,
                            diaryBaseData!!,
                            placeInfoFolder!!,
                            diaryInfoFolder!!
                        )
                    )
                    allDiaryList.add(diaryBulletinData)
                    if (isMyEmail) {
                        myDiaryList.add(diaryBulletinData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}


package com.hansung.traveldiary.src.plan

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityTravelPlanBaseBinding
import com.hansung.traveldiary.src.plan.model.PlanTotalData
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel
import com.hansung.traveldiary.src.travel.PlanBookData
import com.hansung.traveldiary.util.StatusBarUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TravelPlanBaseActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityTravelPlanBaseBinding.inflate(layoutInflater)
    }

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    private lateinit var fragmentManager : FragmentManager;
    private lateinit var transaction : FragmentTransaction
    private lateinit var scheduleFragment : ScheduleFragment
    private lateinit var travelPlanMapFragment : TravelPlanMapFragment
    private lateinit var mapDrawable : Drawable
    private lateinit var scheduleDrawable : Drawable
    private val userPlanDataModel : SharedPlaceViewModel by viewModels()

    private val TAG = "TravelPlanBaseActivity"

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val LOCATION_PERMISSION_FUN_REQUEST_CODE = 1001
        val naverRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val kakaoRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var index = 0
        var planTotalData = PlanTotalData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        println("TravelPlanBaseActivity create")



        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        val menu = intent.getStringExtra("menu")
        index = intent.getIntExtra("index", 0)
        val title = intent.getStringExtra("title")
        if(title != "")
            binding.planTopTitle.text = title

        scheduleFragment = ScheduleFragment(title)
        travelPlanMapFragment = TravelPlanMapFragment(title)

        user = Firebase.auth.currentUser
        db = Firebase.firestore
        db!!.collection(user!!.email.toString()).document(title.toString())
            .get().addOnSuccessListener  { documentSnapshot ->
                val data = documentSnapshot.toObject<PlanTotalData>()!!
                userPlanDataModel.putPlaceAll(data.dayList[index].placeInfoArray)
                planTotalData = data

                if(menu == "schedule"){
                    binding.planTopMenu.setImageDrawable(mapDrawable)
                    transaction.replace(R.id.tp_fragment, scheduleFragment).commitAllowingStateLoss()
                }else{
                    binding.planTopMenu.setImageDrawable(scheduleDrawable)
                    transaction.replace(R.id.tp_fragment, travelPlanMapFragment).commitAllowingStateLoss()
                }
            }


        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()

        mapDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_map_black, null)!!
        scheduleDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_schedule_black, null)!!

        binding.planTopMenu.setOnClickListener{
            println("클릭")
            transaction = fragmentManager.beginTransaction()
            if(binding.planTopMenu.drawable == mapDrawable){
//                transaction.remove(scheduleFragment)
                println("Map으로 체인지")
                binding.planTopMenu.setImageDrawable(scheduleDrawable)
                transaction.replace(R.id.tp_fragment, travelPlanMapFragment).commit()
                return@setOnClickListener
            }else{
                println("Schedule로 체인지")
//                transaction.remove(travelPlanMapFragment)
                binding.planTopMenu.setImageDrawable(mapDrawable)
                transaction.replace(R.id.tp_fragment, scheduleFragment).commit()
                return@setOnClickListener
            }
        }


        binding.planTopBack.setOnClickListener{
            finish()
        }


    }

    override fun onStart() {
        super.onStart()
        println("TravelPlanBaseActivity start")
    }
}

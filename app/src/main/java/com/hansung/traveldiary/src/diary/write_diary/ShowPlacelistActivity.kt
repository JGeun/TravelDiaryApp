package com.hansung.traveldiary.src.diary.write_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.src.DiaryInfo
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.PlaceInfo
import com.hansung.traveldiary.src.PlaceInfoFolder2
import com.hansung.traveldiary.src.diary.write_diary.show_plan.PlacelistFragment
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel
import java.text.SimpleDateFormat
import java.util.*

class ShowPlacelistActivity : AppCompatActivity() {
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private lateinit var transaction : FragmentTransaction
//    private val userPlanDataModel : SharedPlaceViewModel by viewModels()
    private val userDiaryDataModel : SharedPlaceViewModel by viewModels()
    var placeInfoFolder = PlaceInfoFolder2()
    private var day = 0

    companion object{
        var index = 0
        var placeInfo = PlaceInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_placelist)

        index = intent.getIntExtra("index", 0)
        day = intent.getIntExtra("day", 0)
//        var title = MainActivity.userDiaryArray[index].baseData.title

        Log.d("인덱스, 데이", "$index / $day")

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        initViewModel()

//        transaction = supportFragmentManager.beginTransaction()
//        var fragment = PlacelistFragment(title)
//        fragment.setIdx(index)
//        transaction.replace(R.id.view_show, fragment).commit()

    }

    fun initViewModel(){
        db!!.collection("Diary").document(user!!.email.toString())
            .collection("DiaryData").document(MainActivity.userDiaryArray[index].baseData.idx.toString())
            .collection("DayList").document(afterDate(MainActivity.userDiaryArray[index].baseData.startDate, day))
            .get().addOnSuccessListener { documentSnapshot->
                Log.d("플레이스리스트", "load")
                val data = documentSnapshot.toObject<DiaryInfo>()
                if(data != null) {
                    userDiaryDataModel.putAllData(data.placeInfo)
                }
                transaction = supportFragmentManager.beginTransaction()
                var fragment = PlacelistFragment(index, day)
//                fragment.setIdx(index)
                transaction.replace(R.id.view_show, fragment).commit()

            }
//        userDocRef.collection(user!!.email.toString()).document("Diary").collection(title).document("PlanPlaceInfo")
//            .get().addOnSuccessListener  { documentSnapshot ->
//                placeInfo = documentSnapshot.toObject<PlaceInfo>()!!
//                userPlanDataModel.putAllData(placeInfo)
//
//                transaction = supportFragmentManager.beginTransaction()
//                var fragment = PlacelistFragment(title)
//                fragment.setIdx(index)
//                transaction.replace(R.id.view_show, fragment).commit()
//            }

    }

    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }

}
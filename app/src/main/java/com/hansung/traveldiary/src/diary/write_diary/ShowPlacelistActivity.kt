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
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.PlaceInfo
import com.hansung.traveldiary.src.PlaceInfoFolder2
import com.hansung.traveldiary.src.diary.write_diary.show_plan.PlacelistFragment
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class ShowPlacelistActivity : AppCompatActivity() {
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private lateinit var transaction : FragmentTransaction
    private val userPlanDataModel : SharedPlaceViewModel by viewModels()
    var placeInfoFolder = PlaceInfoFolder2()

    companion object{
        var index = 0
        var placeInfo = PlaceInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_placelist)

        index = intent.getIntExtra("index", 0)
        var title = MainActivity.myDiaryList[index].planTitle

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        initViewModel(title!!)

//        transaction = supportFragmentManager.beginTransaction()
//        var fragment = PlacelistFragment(title)
//        fragment.setIdx(index)
//        transaction.replace(R.id.view_show, fragment).commit()

    }

    fun initViewModel(title: String){
        val userDocRef = db!!.collection("User").document("UserData")

        Log.d("플레이스 사이즈", userDocRef.collection(user!!.email.toString()).document("Diary").collection(title).document("PlanPlaceInfo").get().toString())

        userDocRef.collection(user!!.email.toString()).document("Diary").collection(title).document("PlanPlaceInfo")
            .get().addOnSuccessListener  { documentSnapshot ->
                placeInfo = documentSnapshot.toObject<PlaceInfo>()!!
                userPlanDataModel.putAllData(placeInfo)

                transaction = supportFragmentManager.beginTransaction()
                var fragment = PlacelistFragment(title)
                fragment.setIdx(index)
                transaction.replace(R.id.view_show, fragment).commit()
            }

    }

}
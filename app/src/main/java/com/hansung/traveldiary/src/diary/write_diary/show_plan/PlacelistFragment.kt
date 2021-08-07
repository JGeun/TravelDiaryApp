package com.hansung.traveldiary.src.diary.write_diary.show_plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.FragmentPlacelistBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.diary.write_diary.ShowPlacelistActivity
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class PlacelistFragment(val index: Int, val day: Int) : Fragment(){
    private lateinit var binding : FragmentPlacelistBinding
    val userPlaceDataModel : SharedPlaceViewModel by activityViewModels()
    private var title : String? = null
//    private var user: FirebaseUser? = null
//    private var db: FirebaseFirestore? = null
//    var index = 0

//    constructor(title: String?) : this() {
//        this.title = title
//    }

    companion object{
        var checked = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlacelistBinding.inflate(inflater, container, false)
//        user = Firebase.auth.currentUser
//        db = Firebase.firestore

        binding.placelistRecyclerview.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            var scheduleadapter = PlacelistAdapter(userPlaceDataModel, index, day, binding.tvChecked)
//            scheduleadapter.title = title
            adapter = scheduleadapter
        }

//        binding.tvChecked.setOnClickListener {
//            checked = false
////            val userDocRef = db!!.collection("User").document("UserData")
////            userDocRef.collection(user!!.email.toString()).document("Diary").collection(title!!).document("PlanPlaceInfo")
////                .set(ShowPlacelistActivity.placeInfo)
//
//            //MainActivity에 데이터 넣기
//            var day = MainActivity().afterDate(MainActivity.userDiaryArray[index].baseData.startDate, 0).toInt()
//            var placeinfo = MainActivity.userDiaryArray[index].diaryArray[day].placeInfo
//            binding.tvChecked.visibility = View.GONE
//            binding.placelistRecyclerview.adapter?.notifyDataSetChanged()
//        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        println("Placelist fragment start")


        if(userPlaceDataModel.items.placeFolder.size != 0){
            binding.scheduleNoPlan.isVisible = false
            binding.placelistRecyclerview.isVisible = true
        }else{
            binding.scheduleNoPlan.isVisible = true
            binding.placelistRecyclerview.isVisible = false
        }
//        if (checked) {
//            binding.tvChecked.visibility = View.VISIBLE
//        }else{
//            binding.tvChecked.visibility = View.GONE
//        }

    }

//    fun setIdx(idx : Int){
//        index = idx
//    }
}
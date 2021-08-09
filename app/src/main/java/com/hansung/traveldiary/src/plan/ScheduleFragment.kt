package com.hansung.traveldiary.src.plan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentScheduleBinding
import com.hansung.traveldiary.src.DiaryBaseData
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserDiaryData
import com.hansung.traveldiary.src.plan.adapter.ScheduleAdapter
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFragment(val index: Int, val day: Int) : Fragment(){
    private lateinit var binding : FragmentScheduleBinding
    val userPlaceDataModel : SharedPlaceViewModel by activityViewModels()
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    var userDiaryArray = ArrayList<UserDiaryData>() //나의 diary data 리스트
    private lateinit var diaryBaseData:DiaryBaseData
    companion object{
        var checked = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        user = Firebase.auth.currentUser
        db = Firebase.firestore
        diaryBaseData= DiaryBaseData()
        userDiaryArray=ArrayList<UserDiaryData>()
        binding.scheduleRecyclerview.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ScheduleAdapter(userPlaceDataModel, index, day, binding.tvChecked)
        }
        val userRef = db!!.collection("UserInfo").document(user!!.email.toString())
        println("------------------------------------------------------------")
        //println(MainActivity.userDiaryArray[index].baseData.color)
        when(MainActivity.userDiaryArray[index].baseData.color){
            "pink"->{
                binding.scheduleNoPlan.setBackgroundResource(R.drawable.bg_pink_plan)
            }
            "sky"->{
                binding.scheduleNoPlan.setBackgroundResource(R.drawable.bg_sky_plan)

            }
            "yellow"->{
                binding.scheduleNoPlan.setBackgroundResource(R.drawable.bg_yellow_plan)
            }
            "orange"->{
                binding.scheduleNoPlan.setBackgroundResource(R.drawable.bg_orange_plan)

            }
            "purple"->{
                binding.scheduleNoPlan.setBackgroundResource(R.drawable.bg_purple_plan)
            }
            "test_color"->{
                binding.scheduleNoPlan.setBackgroundResource(R.drawable.bg_pink_plan)
            }
            "mapLineColor"->{
                binding.scheduleNoPlan.setBackgroundResource(R.drawable.bg_main_color_plan)
            }
        }

        //println(db!!.collection("Diary").document(user?.email.toString()).collection("DiaryData").document("37997867").get())
        //println(userDiaryArray[0].baseData.color)
        //userDiaryArray.add()


        userPlaceDataModel.userPlanData.observe(viewLifecycleOwner){
            if(userPlaceDataModel.userPlanData.value!!.placeFolder.size != 0){
                binding.scheduleNoPlan.isVisible = false
                binding.scheduleRecyclerview.isVisible = true
            }else{
                binding.scheduleNoPlan.isVisible = true
                binding.scheduleRecyclerview.isVisible = false
            }
        }



        binding.tvChecked.setOnClickListener {
            checked = false

            MainActivity.userPlanArray[index].placeArray[day] = userPlaceDataModel.items
            db!!.collection("Plan").document(user!!.email.toString())
                .collection("PlanData").document(MainActivity.userPlanArray[index].baseData.idx.toString())
                .collection("PlaceInfo").document(afterDate(MainActivity.userPlanArray[index].baseData.startDate, day))
                .set(userPlaceDataModel.items)

            binding.tvChecked.visibility = View.GONE
            binding.scheduleRecyclerview.adapter?.notifyDataSetChanged()
        }

        return binding.root
    }


    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }
}
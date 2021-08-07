package com.hansung.traveldiary.src.plan

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
import com.hansung.traveldiary.databinding.FragmentScheduleBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.plan.adapter.ScheduleAdapter
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFragment(val index: Int, val day: Int) : Fragment(){
    private lateinit var binding : FragmentScheduleBinding
    val userPlaceDataModel : SharedPlaceViewModel by activityViewModels()
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null


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

        binding.scheduleRecyclerview.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ScheduleAdapter(userPlaceDataModel, index, binding.tvChecked)
        }

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
            db!!.collection("Plan")
                .document(user!!.email.toString()).collection("PlanData")
                .document(MainActivity.userPlanArray[index].planBaseData.idx.toString())
                .collection("PlaceInfo").document(afterDate(MainActivity.userPlanArray[index].planBaseData.startDate,day))
                .set(MainActivity.userPlanArray[index].placeArray[day])

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
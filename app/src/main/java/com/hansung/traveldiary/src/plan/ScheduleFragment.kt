package com.hansung.traveldiary.src.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.databinding.FragmentScheduleBinding
import com.hansung.traveldiary.src.plan.adapter.ScheduleAdapter
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class ScheduleFragment() : Fragment(){
    private lateinit var binding : FragmentScheduleBinding
    val userPlaceDataModel : SharedPlaceViewModel by activityViewModels()
    private var title : String? = null

    constructor(title: String?) : this() {
        this.title = title
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.scheduleRecyclerview.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ScheduleAdapter(userPlaceDataModel)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        println("schedule fragment start")
        if(userPlaceDataModel.items.dayPlaceList[TravelPlanBaseActivity.index].placeInfoArray.size != 0){
            binding.scheduleNoPlan.isVisible = false
            binding.scheduleRecyclerview.isVisible = true
        }else{
            binding.scheduleNoPlan.isVisible = true
            binding.scheduleRecyclerview.isVisible = false
        }
    }
}
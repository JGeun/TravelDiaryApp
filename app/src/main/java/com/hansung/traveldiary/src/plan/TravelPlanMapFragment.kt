package com.hansung.traveldiary.src.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hansung.traveldiary.databinding.FragmentPlanMapBinding
import com.hansung.traveldiary.databinding.FragmentScheduleBinding

class TravelPlanMapFragment : Fragment(){
    private lateinit var binding : FragmentPlanMapBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanMapBinding.inflate(inflater, container, false)

        return binding.root
    }
}
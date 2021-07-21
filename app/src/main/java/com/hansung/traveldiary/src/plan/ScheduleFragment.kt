package com.hansung.traveldiary.src.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hansung.traveldiary.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment(){
    private lateinit var binding : FragmentScheduleBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        return binding.root
    }
}
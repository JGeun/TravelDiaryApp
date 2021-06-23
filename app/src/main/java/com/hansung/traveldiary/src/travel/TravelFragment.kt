package com.hansung.traveldiary.src.travel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hansung.traveldiary.databinding.FragmentHomeBinding
import com.hansung.traveldiary.databinding.FragmentTravelBinding

class TravelFragment : Fragment(){
    private lateinit var binding : FragmentTravelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTravelBinding.inflate(inflater, container, false)

        return binding.root
    }
}
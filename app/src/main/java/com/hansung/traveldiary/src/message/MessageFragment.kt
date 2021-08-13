package com.hansung.traveldiary.src.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hansung.traveldiary.databinding.FragmentMessageBinding
import com.hansung.traveldiary.databinding.FragmentTravelBinding
import com.hansung.traveldiary.src.travel.FragmentAdapter

class MessageFragment : Fragment() {
    private lateinit var binding : FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)

        return binding.root
    }
}
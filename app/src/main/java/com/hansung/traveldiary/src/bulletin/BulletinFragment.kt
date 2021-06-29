package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hansung.traveldiary.databinding.ActivityMainBinding.inflate
import com.hansung.traveldiary.databinding.FragmentBulletinBinding
import com.hansung.traveldiary.databinding.FragmentHomeBinding

class BulletinFragment : Fragment(){
    private lateinit var binding : FragmentBulletinBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBulletinBinding.inflate(inflater, container, false)

        println("준호 test 1 ")
        return binding.root
    }
}
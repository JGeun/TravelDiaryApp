package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentUserNameBinding

class UserNameFragment : Fragment() {
    private val viewModel:SearchWordVIewModel by activityViewModels()
    private lateinit var binding:FragmentUserNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentUserNameBinding.inflate(inflater, container, false)
        viewModel.searchWord.observe(viewLifecycleOwner){
            Log.d("췤","변경된 값은 ${viewModel.searchWord.value}")
        }
        //binding.rv.isVisible=false
        binding.rv.apply {
            setHasFixedSize(true)
            adapter = BulletinAdapter()
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }
}
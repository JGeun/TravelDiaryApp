package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentContentBinding
import com.hansung.traveldiary.databinding.FragmentUserNameBinding

class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentContentBinding.inflate(inflater, container, false)
        binding.rv.apply {
            setHasFixedSize(true)
            adapter = BulletinAdapter()
            layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

}
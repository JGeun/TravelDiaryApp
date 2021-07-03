package com.hansung.traveldiary.src.travel

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentTriptogoBinding

class TriptogoFragment : Fragment() {
    private lateinit var binding: FragmentTriptogoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTriptogoBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(it.context, DiaryActivity::class.java))
        }

        return binding.root
    }

    fun newInstant() : TriptogoFragment
    {
        val args = Bundle()
        val frag = TriptogoFragment()
        frag.arguments = args
        return frag
    }

}
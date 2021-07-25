package com.hansung.traveldiary.src.travel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansung.traveldiary.databinding.FragmentLasttripBinding

class TravelDiarySectionFragment : Fragment() {
    private lateinit var binding : FragmentLasttripBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLasttripBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun newInstant() : TravelDiarySectionFragment
    {
        val args = Bundle()
        val frag = TravelDiarySectionFragment()
        frag.arguments = args
        return frag
    }
}

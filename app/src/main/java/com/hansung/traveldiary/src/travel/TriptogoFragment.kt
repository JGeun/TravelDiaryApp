package com.hansung.traveldiary.src.travel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansung.traveldiary.R

class TriptogoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_triptogo, container, false)
    }

    fun newInstant() : TriptogoFragment
    {
        val args = Bundle()
        val frag = TriptogoFragment()
        frag.arguments = args
        return frag
    }

}
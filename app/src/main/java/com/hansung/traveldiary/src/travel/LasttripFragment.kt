package com.hansung.traveldiary.src.travel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansung.traveldiary.R

class LasttripFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lasttrip, container, false)
    }

    fun newInstant() : LasttripFragment
    {
        val args = Bundle()
        val frag = LasttripFragment()
        frag.arguments = args
        return frag
    }
}

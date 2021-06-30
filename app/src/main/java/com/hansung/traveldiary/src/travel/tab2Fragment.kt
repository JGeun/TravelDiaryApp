package com.hansung.traveldiary.src.travel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansung.traveldiary.R

class tab2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false)
    }

    fun newInstant() : tab1Fragment
    {
        val args = Bundle()
        val frag = tab1Fragment()
        frag.arguments = args
        return frag
    }

}
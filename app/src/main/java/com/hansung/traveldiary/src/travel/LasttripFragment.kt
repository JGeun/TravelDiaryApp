package com.hansung.traveldiary.src.travel

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentBulletinBinding
import com.hansung.traveldiary.databinding.FragmentLasttripBinding
import com.hansung.traveldiary.src.login.LoginActivity

class LasttripFragment : Fragment() {
    private lateinit var binding : FragmentLasttripBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLasttripBinding.inflate(inflater, container, false)

        binding.button2.setOnClickListener {
            startActivity(Intent(it.context, DiaryActivity::class.java))

        }

        return binding.root
    }


    fun newInstant() : LasttripFragment
    {
        val args = Bundle()
        val frag = LasttripFragment()
        frag.arguments = args
        return frag
    }
}

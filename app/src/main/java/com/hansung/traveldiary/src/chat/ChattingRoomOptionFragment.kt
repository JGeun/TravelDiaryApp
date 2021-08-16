package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentChattingRoomOptionBinding

class ChattingRoomOptionFragment: Fragment() {
    private lateinit var binding: FragmentChattingRoomOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingRoomOptionBinding.inflate(inflater, container, false)

        binding.ivPrev.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_frm, NewChatFragment())?.commit()
        }

        binding.tvCheck.setOnClickListener {
            startActivity(Intent(context, ChatActivity::class.java))
        }

        return binding.root
    }
}
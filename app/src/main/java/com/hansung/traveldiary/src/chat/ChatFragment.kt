package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hansung.traveldiary.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private lateinit var binding : FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener{
            startActivity(Intent(context, ChatActivity::class.java))
        }

        return binding.root
    }
}
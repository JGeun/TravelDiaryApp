package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentChatBinding

data class MessageData(val image: Drawable, var name: String, var preview: String, var time: String)

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val userMessageDataList = ArrayList<MessageData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        initUserMessageData()

        binding.messeageRv.apply {
            adapter = ChatRoomAdapter(userMessageDataList)
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
        }

        binding.ivSetting.setOnClickListener {
            startActivity(Intent(context, ChatActivity::class.java))
        }

        return binding.root
    }

    private fun initUserMessageData() {
        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
            MessageData(
                it, "프로필 이름", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:40"
            )
        }?.let {
            userMessageDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
            MessageData(
                it, "박진영", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:41"
            )
        }?.let {
            userMessageDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
            MessageData(
                it, "처돌이", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:42"
            )
        }?.let {
            userMessageDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
            MessageData(
                it, "프로필 이름", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:40"
            )
        }?.let {
            userMessageDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
            MessageData(
                it, "박진영", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:41"
            )
        }?.let {
            userMessageDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
            MessageData(
                it, "처돌이", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:42"
            )
        }?.let {
            userMessageDataList.add(
                it
            )
        }

    }
}
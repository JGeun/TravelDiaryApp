package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentChatBinding
import com.hansung.traveldiary.src.ChatFolder
import com.hansung.traveldiary.src.ChatIdxFolder
import com.hansung.traveldiary.src.MainActivity

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val userMessageDataList = ArrayList<MessageData>()
    private var user : FirebaseUser? = null
    private var db : FirebaseFirestore? = null

    private val TAG = "ChatFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        binding.messeageRv.apply {
            adapter = ChatRoomAdapter(userMessageDataList)
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
        }

        val userChatIdxRef =db!!.collection("UserChat").document(user!!.email.toString())
        userChatIdxRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                "Local"
            else
                "Server"

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "$source data: ${snapshot.data}")
                val data = snapshot.toObject<ChatIdxFolder>()

            } else {
                Log.d(TAG, "$source data: null")
            }
        }


        binding.ivSetting.setOnClickListener {
            startActivity(Intent(context, ChatActivity::class.java))
        }

        return binding.root
    }



//    private fun initUserMessageData() {
//        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
//            MessageData(
//                it, "프로필 이름", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:40"
//            )
//        }?.let {
//            userMessageDataList.add(
//                it
//            )
//        }
//
//        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
//            MessageData(
//                it, "박진영", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:41"
//            )
//        }?.let {
//            userMessageDataList.add(
//                it
//            )
//        }
//
//        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
//            MessageData(
//                it, "처돌이", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:42"
//            )
//        }?.let {
//            userMessageDataList.add(
//                it
//            )
//        }
//
//        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
//            MessageData(
//                it, "프로필 이름", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:40"
//            )
//        }?.let {
//            userMessageDataList.add(
//                it
//            )
//        }
//
//        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
//            MessageData(
//                it, "박진영", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:41"
//            )
//        }?.let {
//            userMessageDataList.add(
//                it
//            )
//        }
//
//        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
//            MessageData(
//                it, "처돌이", "채팅 내용들 어디까지 보이나 라랄라라라라라라라라라라라라라라라라랄ㄹ라", "오전 10:42"
//            )
//        }?.let {
//            userMessageDataList.add(
//                it
//            )
//        }
//
//    }
}
package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentChattingRoomOptionBinding
import com.hansung.traveldiary.src.*
import java.util.*

class ChattingRoomOptionFragment(fragment: NewChatFragment): Fragment() {
    private lateinit var binding: FragmentChattingRoomOptionBinding
    private var fragment = fragment

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
            makeNewChatRoom()
            fragmentManager?.beginTransaction()?.replace(R.id.main_frm, ChatFragment())?.commit()
        }

        return binding.root
    }

    fun makeNewChatRoom() {
        Log.d("에러 체크", "NewChatFragment쪽임? 여긴가?")
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        var chatIdxList = IdxList()
        val chatTotalIdxRef = db.collection("ChatIdxDatabase").document("IdxData")
        chatTotalIdxRef.get()
            .addOnSuccessListener { result ->
                val data = result.toObject<IdxList>()
                if (data != null)
                    chatIdxList = data

                var idx = 0L
                while (true) {
                    idx = makeIdx()
                    if (!chatIdxList.idxFolder.contains(idx)) {
                        break
                    }
                }
                chatIdxList.idxFolder.add(idx)
                chatTotalIdxRef.set(chatIdxList)

                db.collection("ChatData").document(idx.toString())
                    .set(ChatFolder())

                //여기서 유저한테 idx추가 하면 됨
                var chatFolder = ChatIdxFolder()
                val userChatIdxRef = db.collection("UserChat").document(user!!.email.toString())
                userChatIdxRef.get().addOnSuccessListener { result ->
                    val data = result.toObject<ChatIdxFolder>()
                    if (data != null) {
                        chatFolder = data
                    }

                    chatFolder.chatIdxFolder.add(
                        ChatIdxData(
                            idx,
                            UserList(),
                            "${binding.chatroomTitle.text}",
                            "",
                            "",
                            "2021-08-16 17:07:41"
                        )
                    )
                    userChatIdxRef.set(chatFolder).addOnSuccessListener {
                        fragmentManager?.beginTransaction()?.replace(R.id.main_frm, ChatFragment())
                            ?.commit()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(context, "대화방 만들기 실패.", Toast.LENGTH_SHORT).show()
                fragmentManager?.beginTransaction()?.replace(R.id.main_frm, ChatFragment())
                    ?.commit()
            }
    }

    private fun makeIdx(): Long {
        val str = StringBuilder()
        val random = Random()
        for (i in 0 until 8) {
            val num = random.nextInt(9) + 1
            str.append(num.toString())
        }
        return str.toString().toLong()
    }

}
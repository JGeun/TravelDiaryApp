package com.hansung.traveldiary.src.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.ActivityChatBinding
import com.hansung.traveldiary.src.ChatData
import com.hansung.traveldiary.src.ChatFolder
import com.hansung.traveldiary.src.ChatIdxData
import com.hansung.traveldiary.src.ChatIdxFolder
import com.hansung.traveldiary.util.StatusBarUtil
import java.text.SimpleDateFormat
import java.util.*


class ChatActivity : AppCompatActivity() {
    private var chatList = ChatFolder()
    private val binding by lazy{
        ActivityChatBinding.inflate(layoutInflater)
    }

    private val TAG =  "ChatActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.LIGHT_MAIN_STATUS_BAR)
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        val checkUser = user!!.email.toString()
        Log.d("chat", checkUser)
        val chatAdapter = ChatAdapter(checkUser)

        binding.roomTitle.text = intent.getStringExtra("title")

        binding.rv.apply{
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }

        binding.rv.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                Log.d("체크", "적용?")
                binding.rv.postDelayed({
                    if (chatList.chatFolder.size > 0) {
                        binding.rv.scrollToPosition(chatList.chatFolder.size - 1)
                    }
                }, 100)
            }
        }

        val idx = intent.getLongExtra("idx", 0)
        val chatRef = db.collection("ChatData").document(idx.toString())
        if(idx != 0L) {
            chatRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                    "Local"
                else
                    "Server"

                if (snapshot != null && snapshot.exists()) {
                    Log.d("체크", "snapshot")
                    val data = snapshot.toObject<ChatFolder>()
                    if (data != null && data.chatFolder.size != 0) {
                        chatList = data
                        if (chatAdapter.itemCount != 0) {
                            chatAdapter.add(chatList.chatFolder[chatList.chatFolder.size - 1])
                        } else {
                            for (chatData in chatList.chatFolder) {
                                chatAdapter.add(chatData)
                            }
                        }
                        binding.rv.adapter!!.notifyDataSetChanged()
                        Log.d("체크", "item: ${binding.rv.adapter!!.itemCount}")
                        binding.rv.scrollToPosition(chatList.chatFolder.size - 1)
                    }
                } else {
                    Log.d(TAG, "$source data: null")
                }
            }
        }else{
            Toast.makeText(this, "존재하지 않는 채팅방입니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.send.setOnClickListener{
            val text = binding.input.text.toString()
            if(text != ""){
                chatList.chatFolder.add(ChatData(checkUser, text))
                chatRef.set(chatList)
                binding.input.setText("")

                db.collection("UserChat").document(user.email.toString())
                    .get().addOnSuccessListener { result ->
                        val data = result.toObject<ChatIdxFolder>()
                        if(data != null){
                            var index = 0
                            for(i in 0 until data.chatIdxFolder.size){
                                if(data.chatIdxFolder[i].idx == idx){
                                    if(text.length > 10)
                                        data.chatIdxFolder[i].preview = text.substring(0, 10)+"..."
                                    else
                                        data.chatIdxFolder[i].preview = text.substring(0, text.length)+"..."
                                    data.chatIdxFolder[i].lastTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

                                    for(email in data.chatIdxFolder[i].friendsEmailList.emailFolder)
                                        db.collection("UserChat").document(email).set(data)
                                    break
                                }
                            }
                        }
                    }
            }
        }

        binding.back.setOnClickListener {
            finish()
        }

    }
}
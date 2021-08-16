package com.hansung.traveldiary.src.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityChatBinding
import com.hansung.traveldiary.src.plan.KakaoSearchKeywordService
import com.hansung.traveldiary.util.StatusBarUtil

data class ChatFolder(var chatFolder: ArrayList<ChatData> = ArrayList())
data class ChatData(var userEmail: String ="", var contents: String="")

class ChatActivity : AppCompatActivity() {
    private var chatList = ChatFolder()
    private val binding by lazy{
        ActivityChatBinding.inflate(layoutInflater)
    }
    private val TAG =  "ChatActivity"
    private val checkUser = "User2"
    private val chatAdapter = ChatAdapter(checkUser)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.MAIN_STATUS_BAR)
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

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

        val chatRef = db.collection("ChatTest").document("test")
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
                if(data != null && data.chatFolder.size != 0){
                    chatList = data
                    if(chatAdapter.itemCount != 0){
                        chatAdapter.add(chatList.chatFolder[chatList.chatFolder.size-1])
                    }else{
                        for(chatData in chatList.chatFolder){
                            chatAdapter.add(chatData)
                        }
                    }
                    binding.rv.adapter!!.notifyDataSetChanged()
                    Log.d("체크" , "item: ${binding.rv.adapter!!.itemCount}")
                    binding.rv.scrollToPosition(chatList.chatFolder.size-1)
                }
            } else {
                Log.d(TAG, "$source data: null")
            }
        }



        binding.send.setOnClickListener{
            val text = binding.input.text.toString()
            if(text != ""){
                chatList.chatFolder.add(ChatData(checkUser, text))
                chatRef.set(chatList)
                binding.input.setText("")
            }
        }
    }
}
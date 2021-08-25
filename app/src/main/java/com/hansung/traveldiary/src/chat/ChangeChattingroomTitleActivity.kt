package com.hansung.traveldiary.src.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityChangeChattingroomTitleBinding
import com.hansung.traveldiary.src.ChatIdxFolder

class ChangeChattingroomTitleActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityChangeChattingroomTitleBinding.inflate(layoutInflater)
    }
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private var chatIdxFolder = ChatIdxFolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        val userChatIdxRef =
            db!!.collection("UserChat").document(user!!.email.toString())

        val position = intent.getIntExtra("position", -1)

        userChatIdxRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                "Local"
            else
                "Server"

            if (snapshot != null && snapshot.exists()) {
                val data = snapshot.toObject<ChatIdxFolder>()
                if (data != null) {
                    chatIdxFolder = data
                }
            } else {
            }
        }

        binding.ivPrev.setOnClickListener {
            finish()
        }

        binding.tvCheck.setOnClickListener {
            chatIdxFolder.chatIdxFolder[position].title = binding.chatroomTitle.text.toString()
            userChatIdxRef.set(chatIdxFolder)
            finish()
        }

    }
}
package com.hansung.traveldiary.src.chat

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityEditChatroomBinding
import com.hansung.traveldiary.databinding.DialogEditChatroomBinding
import com.hansung.traveldiary.src.ChatIdxData
import com.hansung.traveldiary.src.ChatIdxFolder
import kotlin.math.log

class EditChatroomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditChatroomBinding
    private var chatIdxFolder = ChatIdxFolder()
    private val TAG = "EditChatroomActivity"

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditChatroomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        val userChatIdxRef =
            db!!.collection("UserChat").document(user!!.email.toString())

        val position = intent.getIntExtra("position", -1)
        val username =  intent.getStringExtra("username")

        binding.userName.text = username

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
                if (data != null) {
                    chatIdxFolder = data
                }
            } else {
                Log.d(TAG, "$source data: null")
            }
        }

        binding.setChatroom.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("채팅방 이름 설정")
            builder.setView(layoutInflater.inflate(R.layout.dialog_edit_chatroom, null))

            val listener = DialogInterface.OnClickListener { dialog, which ->
                var alertDialog = dialog as AlertDialog
                var chatroomTitle: EditText? = alertDialog.findViewById<EditText>(R.id.tv_edit_chatroom)

                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> return@OnClickListener
                    DialogInterface.BUTTON_NEGATIVE -> {
                        chatIdxFolder.chatIdxFolder[position].title = chatroomTitle?.text.toString()
                        userChatIdxRef.set(chatIdxFolder)
                        finish()
                    }
                }

            }

            builder.setPositiveButton("취소", listener)
            builder.setNegativeButton("확인", listener)

            builder.show()
        }

        binding.tvExit.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setMessage("채팅방에서 나가시겠습니까?")

            var listener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> return
                        DialogInterface.BUTTON_NEGATIVE -> {
                            Log.d("삭제", chatIdxFolder.chatIdxFolder[position].title)
                            chatIdxFolder.chatIdxFolder.removeAt(position)

                            userChatIdxRef.set(chatIdxFolder)
                            finish()
                        }
                    }
                }
            }

            builder.setPositiveButton("취소", listener)
            builder.setNegativeButton("확인", listener)

            builder.show()
        }

        binding.apaOutblock.setOnClickListener {
            println("아웃")
            finish()
            overridePendingTransition(0, 0)
        }


    }



}

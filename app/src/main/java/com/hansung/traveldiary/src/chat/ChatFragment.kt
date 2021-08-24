package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentChatBinding
import com.hansung.traveldiary.src.*
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    private var chatIdxFolder = ChatIdxFolder()
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private var searchChat = ChatIdxFolder()

    private val TAG = "ChatFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        user = Firebase.auth.currentUser
        db = Firebase.firestore

//        binding.messeageRv.apply {
//            adapter = ChatRoomAdapter(userMessageDataList)
//            setHasFixedSize(false)
//            layoutManager = LinearLayoutManager(context)
//        }

        Log.d("에러 체크", "ChatFragment쪽임?")

        binding.searchChatroom.visibility = View.GONE
        binding.ivSearch.visibility = View.GONE
//        binding.ivFind.setOnClickListener {
//            if (binding.searchChatroom.visibility == View.GONE) {
//                binding.searchChatroom.visibility = View.VISIBLE
//                binding.ivSearch.visibility = View.VISIBLE
//            }else{
////                chatIdxFolder.chatIdxFolder.addAll(searchChat.chatIdxFolder)
////                binding.messeageRv.adapter?.notifyDataSetChanged()
//                binding.ivSearch.visibility = View.GONE
//                binding.searchChatroom.visibility = View.GONE
//            }
//        }

//        binding.searchChatroom.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                var text = binding.searchChatroom.text.toString()
//                Log.d("검색", text)
//                searchChatroom(text)
//                if (text.isEmpty())
//                    searchEmptyChatroom()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//        })

        binding.ivNewChat.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_frm, NewChatFragment())?.commit()
        }

        binding.ivSetting.setOnClickListener {
            startActivity(Intent(context, ChatActivity::class.java))
        }

        return binding.root
    }


    override fun onStart() {
        super.onStart()

        val userChatIdxRef = db!!.collection("UserChat").document(user!!.email.toString())
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
                    searchChat = data
                    if (chatIdxFolder.chatIdxFolder.size == 0) {
                        binding.chatNoRooms.visibility = View.VISIBLE
                        binding.messeageRv.visibility = View.INVISIBLE
                    } else {
//                        initUserMessageData();
                        binding.chatNoRooms.visibility = View.INVISIBLE
                        binding.messeageRv.apply {
                            adapter = ChatRoomAdapter(chatIdxFolder)
                            setHasFixedSize(false)
                            layoutManager = LinearLayoutManager(context)
                        }
                        binding.messeageRv.setItemViewCacheSize(chatIdxFolder.chatIdxFolder.size)
                    }

                } else {
                    binding.chatNoRooms.visibility = View.VISIBLE
                    binding.messeageRv.visibility = View.INVISIBLE
                }
            } else {
                Log.d(TAG, "$source data: null")
            }
        }
    }

    fun searchChatroom(search: String){
        chatIdxFolder.chatIdxFolder.clear()
        Log.d("첫번째", searchChat.chatIdxFolder[0].title)

            for(i in 0..searchChat.chatIdxFolder.size-1){
                if (searchChat.chatIdxFolder[i].title.contains(search)){
                    chatIdxFolder.chatIdxFolder.add(searchChat.chatIdxFolder[i])
                }
            }


        binding.messeageRv.adapter?.notifyDataSetChanged()
    }

    fun searchEmptyChatroom(){
        Log.d("문자입력없음", "searchemptyChatroom")
        chatIdxFolder.chatIdxFolder.clear()
        chatIdxFolder.chatIdxFolder.addAll(searchChat.chatIdxFolder)
        Log.d("첫번째", searchChat.chatIdxFolder[0].title)
        binding.messeageRv.adapter?.notifyDataSetChanged()
    }

    /*private fun initUserMessageData() {
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

    }*/
}
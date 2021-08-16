package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentNewChatBinding
import com.hansung.traveldiary.src.*
import java.util.*
import kotlin.collections.ArrayList

data class UserData(val image: Drawable, var name: String)

class NewChatFragment : Fragment() {
    private lateinit var binding: FragmentNewChatBinding
    private val usersDataList = ArrayList<UserData>()
//    companion object{
//        val selectedusersDataList = ArrayList<UserData>()
//    }

    private val TAG = "NewChatFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewChatBinding.inflate(inflater, container, false)

        initUsersData()

        binding.selectedUsersRv.apply {
            adapter = SelectedUsersAdapter(usersDataList)
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.usersRv.apply {
            adapter = NewChatAdapter(usersDataList)
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
        }

        binding.ivBack.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_frm, ChatFragment())?.commit()
        }

        binding.tvMake.setOnClickListener {
            Log.d("에러 체크", "NewChatFragment쪽임?")
//            if (usersDataList.size==1){
            makeNewChatRoom()
//            startActivity(Intent(context, ChatActivity::class.java))

//            }else{
//                fragmentManager?.beginTransaction()?.replace(R.id.main_frm, ChattingRoomOptionFragment())?.commit()
//            }
        }

        return binding.root
    }

    private fun makeNewChatRoom() {
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
                                "테스트용",
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

    private fun initUsersData() {
        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
            UserData(
                it, "프로필 이름"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)?.let {
            UserData(
                it, "강아쥐"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)?.let {
            UserData(
                it, "고양이"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

        ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)?.let {
            UserData(
                it, "말"
            )
        }?.let {
            usersDataList.add(
                it
            )
        }

    }
}
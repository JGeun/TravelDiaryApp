package com.hansung.traveldiary.src.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentNewChatBinding
import com.hansung.traveldiary.src.*
import kotlinx.coroutines.selects.select
import java.util.*
import kotlin.collections.ArrayList


data class FriendInfo(var nickname: String = "", var imagePath: String = "", var selected: Boolean) :
    Comparable<FriendInfo> {
    override fun compareTo(other: FriendInfo): Int {
        return this.nickname.compareTo(other.nickname)
    }
}

class NewChatFragment : Fragment() {
    private lateinit var binding: FragmentNewChatBinding
    val selectedArray = ArrayList<FriendInfo>()
    val friendArray = ArrayList<FriendInfo>()

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private lateinit var friendListAdapter: ChatFriendListAdapter
    private val TAG = "NewChatFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewChatBinding.inflate(inflater, container, false)
        user = Firebase.auth.currentUser

        db = Firebase.firestore



//        binding.selectedUsersRv.visibility = View.INVISIBLE
        binding.selectedUsersRv.adapter = SelectedUsersAdapter(selectedArray, this)
        binding.selectedUsersRv.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }


        for (friendEmail in MainActivity.myFriendList.friendFolder) {
            for (i in 0 until MainActivity.userInfoList.size) {
                if (MainActivity.userInfoList[i].email == friendEmail) {
                    val friendInfo = FriendInfo(
                        MainActivity.userInfoList[i].nickname,
                        MainActivity.userInfoList[i].profileImage,
                        false
                    )
                    friendArray.add(friendInfo)
                    break
                }
            }
        }

        binding.usersRv.adapter = ChatFriendListAdapter(friendArray, this)
        binding.usersRv.apply {
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

    fun notifySelectedArr(data: FriendInfo, selected: Boolean){
        if (selected){
            selectedArray.add(data)
        }else{
            selectedArray.remove(data)
        }
        Log.d("NOTIFY", "작동함!")
        binding.selectedUsersRv.adapter?.notifyDataSetChanged()
    }

    fun notifyFreindArr(data: FriendInfo){
        var idx = friendArray.indexOf(data)
        data.selected = false
        friendArray.set(idx, data)
        Log.d("유저리스트", "${idx}, $friendArray")  //selected true/false는 어댑터에서만 적용
        binding.usersRv.adapter?.notifyDataSetChanged()
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

    private fun showCustomToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
package com.hansung.traveldiary.src.travel.AddBook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.ActivityAtpAddFriendsBinding
import com.hansung.traveldiary.databinding.FragmentNewChatBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.chat.ChatFriendListAdapter
import com.hansung.traveldiary.src.travel.AddBook.adapter.AtpFriendListAdapter
import com.hansung.traveldiary.src.travel.AddBook.adapter.SelectedFriendsAdapter

data class FriendInfo(
    var email: String,
    var nickname: String = "",
    var imagePath: String = "",
    var selected: Boolean
) :
    Comparable<FriendInfo> {
    override fun compareTo(other: FriendInfo): Int {
        return this.nickname.compareTo(other.nickname)
    }
}

class AtpAddFriendsActivity : AppCompatActivity() {
    private val binding: ActivityAtpAddFriendsBinding by lazy {
        ActivityAtpAddFriendsBinding.inflate(layoutInflater)
    }
    val selectedArray = ArrayList<FriendInfo>()
    val friendArray = ArrayList<FriendInfo>()
    val searchArray = ArrayList<FriendInfo>()

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private lateinit var friendListAdapter: ChatFriendListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = Firebase.auth.currentUser

        db = Firebase.firestore

//        binding.selectedUsersRv.visibility = View.INVISIBLE
        binding.selectedUsersRv.adapter = SelectedFriendsAdapter(selectedArray, this)
        binding.selectedUsersRv.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.searchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text = binding.searchUser.text.toString()
                Log.d("검색", text)
                searchChatroom(text)
//                if (text.isEmpty())
//                    searchEmptyChatroom()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


        for (friendEmail in MainActivity.myFriendList.friendFolder) {
            for (i in 0 until MainActivity.userInfoList.size) {
                if (MainActivity.userInfoList[i].email == friendEmail) {
                    val friendInfo = FriendInfo(
                        MainActivity.userInfoList[i].email,
                        MainActivity.userInfoList[i].nickname,
                        MainActivity.userInfoList[i].profileImage,
                        false
                    )
                    friendArray.add(friendInfo)
                    searchArray.add(friendInfo)
                    break
                }
            }
        }

        binding.usersRv.adapter = AtpFriendListAdapter(friendArray, this)
        binding.usersRv.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvMake.setOnClickListener {
            //완료 누르면 db에 친구 이메일리스트 저장

            finish()
        }

    }
    fun searchChatroom(search: String) {
        friendArray.clear()

        for (i in 0..searchArray.size - 1) {
            if (searchArray[i].nickname.contains(search)) {
                friendArray.add(searchArray[i])
            }
        }

        binding.usersRv.adapter?.notifyDataSetChanged()
    }

    fun notifySelectedArr(data: FriendInfo, selected: Boolean) {
        if (selected) {
            selectedArray.add(data)
        } else {
            selectedArray.remove(data)
        }
        Log.d("NOTIFY", "작동함!")
        binding.selectedUsersRv.adapter?.notifyDataSetChanged()
    }

    fun notifyFreindArr(data: FriendInfo) {
        var idx = friendArray.indexOf(data)
        data.selected = false
        friendArray.set(idx, data)
        Log.d("유저리스트", "${idx}, $friendArray")  //selected true/false는 어댑터에서만 적용
        binding.usersRv.adapter?.notifyDataSetChanged()
    }

}
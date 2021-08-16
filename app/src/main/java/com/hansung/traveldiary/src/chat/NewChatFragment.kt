package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.auth.User
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentNewChatBinding

data class UserData(val image: Drawable, var name: String)

class NewChatFragment : Fragment() {
    private lateinit var binding : FragmentNewChatBinding
    private val usersDataList = ArrayList<UserData>()
//    companion object{
//        val selectedusersDataList = ArrayList<UserData>()
//    }

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
            if (usersDataList.size==1){
                startActivity(Intent(context, ChatActivity::class.java))
            }else{
                fragmentManager?.beginTransaction()?.replace(R.id.main_frm, ChattingRoomOptionFragment())?.commit()
            }
        }

        return binding.root
    }

    private fun initUsersData(){
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
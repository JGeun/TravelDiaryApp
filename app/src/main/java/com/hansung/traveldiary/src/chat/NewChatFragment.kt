package com.hansung.traveldiary.src.chat

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentNewChatBinding

data class UserData(val image: Drawable, var name: String)

class NewChatFragment : Fragment() {
    private lateinit var binding : FragmentNewChatBinding
    private val usersDataList = ArrayList<UserData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewChatBinding.inflate(inflater, container, false)

        initUsersData()

        binding.newChatRv.apply {
            adapter = NewChatAdapter(usersDataList)
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
        }

        binding.ivBack.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_frm, ChatFragment())?.commit()
        }

        binding.tvMake.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_frm, ChattingRoomOptionFragment())?.commit()
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

    }
}
package com.hansung.traveldiary.src.chat

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemNewChatBinding

class ChatFriendListAdapter(val friendInfoList: ArrayList<FriendInfo> = ArrayList<FriendInfo>(), val fragment: NewChatFragment) : RecyclerView.Adapter<ChatFriendListAdapter.ViewHolder>(){

    class ViewHolder(binding: ItemNewChatBinding) : RecyclerView.ViewHolder(binding.root){
        val userImage = binding.userImage
        val userName = binding.userName
        val userRb = binding.userRb
        val userRg = binding.userRg
        val background = binding.userBg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewChatBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val imagePath = friendInfoList[position].imagePath
        if(imagePath != "")
            Glide.with(context).load(imagePath).circleCrop().into(holder.userImage)
        else
            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_beach, null)).circleCrop().into(holder.userImage)
        holder.userName.text = friendInfoList[position].nickname

        if(!friendInfoList[position].selected){
            holder.userRb.isChecked = false
            holder.userRg.clearCheck()
            holder.background.setBackgroundColor(Color.WHITE)
        }else{
            friendInfoList[position].selected = true
            holder.userRb.isChecked = true
            holder.background.setBackgroundColor(Color.parseColor("#F5F1F6"))
        }

        holder.itemView.setOnClickListener{
            if(friendInfoList[position].selected){
                Log.d("채팅", "${position}, ${holder.userRb.isChecked}")
                friendInfoList[position].selected = false
                holder.userRb.isChecked = false
                holder.userRg.clearCheck()
                holder.background.setBackgroundColor(Color.WHITE)
                fragment.notifySelectedArr(friendInfoList[position], false)
            }else{
                Log.d("채팅", "${position}, ${holder.userRb.isChecked}")
                friendInfoList[position].selected = true
                holder.userRb.isChecked = true
                holder.background.setBackgroundColor(Color.parseColor("#F5F1F6"))
                fragment.notifySelectedArr(friendInfoList[position], true)
            }
        }
    }

    override fun getItemCount(): Int = friendInfoList.size

    fun addFriend(info: FriendInfo){
        friendInfoList.add(info)
    }

}
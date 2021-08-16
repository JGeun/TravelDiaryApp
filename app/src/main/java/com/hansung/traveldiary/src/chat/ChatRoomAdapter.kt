package com.hansung.traveldiary.src.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.databinding.ItemChatRoomBinding

class ChatRoomAdapter(private val userMessageData: ArrayList<MessageData>):RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>(){
    class ViewHolder(binding: ItemChatRoomBinding):RecyclerView.ViewHolder(binding.root) {
        val userProfileImage: ImageView
        val userName: TextView
        val userPreview: TextView
        val currentTime: TextView

        init {
            userProfileImage = binding.userProfileImage
            userName = binding.userName
            userPreview = binding.userPreview
            currentTime = binding.currentTime
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(userMessageData[position].image).apply(RequestOptions().circleCrop()).into(holder.userProfileImage)
        holder.userName.text = userMessageData[position].name
        holder.userPreview.text = userMessageData[position].preview
        holder.currentTime.text = userMessageData[position].time
        
        holder.itemView.setOnClickListener { 
            Log.d("채팅방 클릭", "${holder.userName}")
        }
    }

    override fun getItemCount(): Int = userMessageData.size

}
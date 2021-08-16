package com.hansung.traveldiary.src.chat

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemChatRoomBinding
import com.hansung.traveldiary.src.ChatIdxFolder

class ChatRoomAdapter(private val chatIdxFolder: ChatIdxFolder):RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>(){
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
        val data = chatIdxFolder.chatIdxFolder
        val context = holder.itemView.context
        val tempImage = ResourcesCompat.getDrawable(context.resources, R.drawable.img_beach, null)
        if(data[position].image != ""){
            Glide.with(context).load(data[position].image).apply(RequestOptions().circleCrop()).into(holder.userProfileImage)
        }else{
            Glide.with(context).load(tempImage).apply(RequestOptions().circleCrop()).into(holder.userProfileImage)
        }

        holder.userName.text = data[position].title
        holder.userPreview.text = data[position].preview
        holder.currentTime.text = data[position].lastTime
        
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("idx", data[position].idx)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = chatIdxFolder.chatIdxFolder.size

}
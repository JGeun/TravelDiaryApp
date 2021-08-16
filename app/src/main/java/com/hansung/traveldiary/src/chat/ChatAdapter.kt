package com.hansung.traveldiary.src.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemChatBinding
import com.hansung.traveldiary.src.ChatData
import com.hansung.traveldiary.src.ChatFolder

class ChatAdapter(val myUser : String) : RecyclerView.Adapter<ChatAdapter.ViewHolder>(){
    private val chatList = ChatFolder()
    class ViewHolder(binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root){
        val myLayout = binding.myChat
        val myImage = binding.myImage
        val myName = binding.myName
        val myContents = binding.myContents

        val userLayout = binding.userChat
        val userImage = binding.userImage
        val userName = binding.userName
        val userContents = binding.userContents
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        if(chatList.chatFolder[position].userEmail == myUser){
            holder.myLayout.visibility = View.VISIBLE
            holder.userLayout.visibility = View.INVISIBLE
            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.bg_profile, null)).into(holder.myImage)
            holder.myName.text = chatList.chatFolder[position].userEmail
            holder.myContents.text = chatList.chatFolder[position].contents
        }else{
            holder.myLayout.visibility = View.INVISIBLE
            holder.userLayout.visibility = View.VISIBLE
            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.bg_profile, null)).into(holder.userImage)
            holder.userName.text = chatList.chatFolder[position].userEmail
            holder.userContents.text = chatList.chatFolder[position].contents
        }
    }

    override fun getItemCount(): Int = chatList.chatFolder.size

    fun add(data : ChatData){
        chatList.chatFolder.add(data)
    }
}
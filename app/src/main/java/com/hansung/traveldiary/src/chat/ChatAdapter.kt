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
import com.hansung.traveldiary.src.MainActivity

class ChatAdapter(private val myUser : String) : RecyclerView.Adapter<ChatAdapter.ViewHolder>(){
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
//            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_beach, null)).into(holder.myImage)

            var myname = ""
            var myImagePath = ""
            for(i in 0..MainActivity.userInfoList.size-1){
                if(chatList.chatFolder[position].userEmail == MainActivity.userInfoList[i].email) {
                    myname = MainActivity.userInfoList[i].nickname
                    myImagePath = MainActivity.userInfoList[i].profileImage
                }
            }

            if(myImagePath == "")
                Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_user, null)).circleCrop().into(holder.myImage)
            else
                Glide.with(context).load(myImagePath).circleCrop().into(holder.userImage)
            holder.myName.text = myname
            holder.myContents.text = chatList.chatFolder[position].contents
            holder.myContents.background = ResourcesCompat.getDrawable(context.resources, R.drawable.bg_my_chat, null)
        }else{
            holder.myLayout.visibility = View.INVISIBLE
            holder.userLayout.visibility = View.VISIBLE


            var userImagePath = ""
            var username = ""
            for(i in 0..MainActivity.userInfoList.size-1){
                if(chatList.chatFolder[position].userEmail == MainActivity.userInfoList[i].email) {
                    username = MainActivity.userInfoList[i].nickname
                    userImagePath = MainActivity.userInfoList[i].profileImage
                }
            }

//            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_seoul_cup, null)).into(holder.userImage)
            if(userImagePath == "")
                Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_user, null)).circleCrop().into(holder.userImage)
            else
                Glide.with(context).load(userImagePath).circleCrop().into(holder.userImage)
            holder.userName.text = username
            holder.userContents.text = chatList.chatFolder[position].contents
            holder.userContents.background = ResourcesCompat.getDrawable(context.resources, R.drawable.bg_not_my_chat, null)
        }
    }

    override fun getItemCount(): Int = chatList.chatFolder.size

    fun add(data : ChatData){
        chatList.chatFolder.add(data)
    }
}
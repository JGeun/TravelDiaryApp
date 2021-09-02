package com.hansung.traveldiary.src.travel.AddBook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemSelectedUsersBinding
import com.hansung.traveldiary.src.chat.NewChatFragment
import com.hansung.traveldiary.src.travel.AddBook.AtpAddFriendsActivity
import com.hansung.traveldiary.src.travel.AddBook.FriendInfo

class SelectedFriendsAdapter(val selectedusers: ArrayList<FriendInfo>, val activity: AtpAddFriendsActivity) : RecyclerView.Adapter<SelectedFriendsAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemSelectedUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val userImage = binding.userImage
        val userName = binding.userName
        val DeleteBtn = binding.ivDelete
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemSelectedUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedFriendsAdapter.ViewHolder, position: Int) {
        val context = holder.itemView.context
        val imagePath = selectedusers[position].imagePath
        if(imagePath != "")
            Glide.with(context).load(imagePath).circleCrop().into(holder.userImage)
        else
            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_basic_profile, null)).circleCrop().into(holder.userImage)
        holder.userName.text = selectedusers[position].nickname

        holder.DeleteBtn.setOnClickListener {
            var data = selectedusers[position]
            selectedusers.removeAt(position)
            notifyDataSetChanged()
            activity.notifyFreindArr(data)
        }
    }

    override fun getItemCount(): Int = selectedusers.size
}
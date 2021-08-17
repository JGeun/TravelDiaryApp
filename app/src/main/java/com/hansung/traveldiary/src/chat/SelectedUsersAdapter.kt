package com.hansung.traveldiary.src.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemSelectedUsersBinding

class SelectedUsersAdapter(val selectedusers: ArrayList<FriendInfo>) : RecyclerView.Adapter<SelectedUsersAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemSelectedUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val userImage = binding.userImage
        val userName = binding.userName
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemSelectedUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedUsersAdapter.ViewHolder, position: Int) {
        val context = holder.itemView.context
        Glide.with(context).load(selectedusers[position].imagePath).circleCrop().into(holder.userImage)
        holder.userName.text = selectedusers[position].nickname
    }

    override fun getItemCount(): Int = selectedusers.size
}
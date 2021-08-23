package com.hansung.traveldiary.src.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemCommentsListBinding

class CommentsAdapter():RecyclerView.Adapter<CommentsAdapter.PagerViewHolder>() {
    class PagerViewHolder(val binding:ItemCommentsListBinding)
        :RecyclerView.ViewHolder(binding.root){
        val profileImage=binding.ivProfile
        val nickname=binding.tvNickname
        val comment=binding.tvCommet
        val date=binding.tvDate
        val likeImage=binding.commentLike
        val likelayout=binding.likeLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemCommentsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(R.drawable.bg_profile).circleCrop().into(holder.profileImage)
        var chk_like=false

        holder.likelayout.setOnClickListener {
            if(!chk_like){
                Glide.with(holder.itemView.context).load(R.drawable.asset7).into(holder.likeImage)
                chk_like=true
            }else{
                Glide.with(holder.itemView.context).load(R.drawable.emptyheart).into(holder.likeImage)
                chk_like=false
            }
        }

    }

    override fun getItemCount(): Int = 3



}
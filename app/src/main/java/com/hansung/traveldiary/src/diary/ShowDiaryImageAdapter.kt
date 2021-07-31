package com.hansung.traveldiary.src.diary

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.*
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemDiaryPictureBinding

class ShowDiaryImageAdapter() : RecyclerView.Adapter<ShowDiaryImageAdapter.PagerViewHolder>() {

    class PagerViewHolder(val binding : ItemDiaryPictureBinding) : RecyclerView.ViewHolder(binding.root) {
        var image : ImageView = binding.itemDpImage
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder{
        val binding = ItemDiaryPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val context = holder.itemView.context
        Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_beach, null)).into(holder.image)

    }

    override fun getItemCount(): Int = 3


}


package com.hansung.traveldiary.src.bulletin

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

class ShowDiaryVPAdapter(val imagePathArray: ArrayList<String>) : RecyclerView.Adapter<ShowDiaryVPAdapter.PagerViewHolder>() {

    class PagerViewHolder(val binding : ItemDiaryPictureBinding) : RecyclerView.ViewHolder(binding.root) {
        var image : ImageView = binding.itemDpImage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder{
        val binding = ItemDiaryPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val context = holder.itemView.context
        Glide.with(context).load(imagePathArray[position]).into(holder.image)
    }

    override fun getItemCount(): Int = imagePathArray.size


}


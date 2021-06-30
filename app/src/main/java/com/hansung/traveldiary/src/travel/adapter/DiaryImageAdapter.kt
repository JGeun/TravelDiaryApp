package com.hansung.traveldiary.src.travel.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.databinding.ItemDiaryimageBinding

class DiaryImageAdapter(private val imageData: ArrayList<Drawable>) : RecyclerView.Adapter<DiaryImageAdapter.ViewHolder>() {
    private lateinit var binding : ItemDiaryimageBinding

    class ViewHolder(val binding: ItemDiaryimageBinding) : RecyclerView.ViewHolder(binding.root){
        val diaryImage : ImageView

        init {
            diaryImage = binding.itemDiaryImage
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemDiaryimageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(imageData[position]).apply(RequestOptions()).into(holder.diaryImage)
    }

    override fun getItemCount() = imageData.size

}
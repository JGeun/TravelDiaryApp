package com.hansung.traveldiary.src.diary.write_diary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemWriteDiaryImageBinding

class WriteImageAdapter(val imagePathArray: ArrayList<String>) : RecyclerView.Adapter<WriteImageAdapter.PagerViewHolder>() {

    class PagerViewHolder(val binding : ItemWriteDiaryImageBinding) : RecyclerView.ViewHolder(binding.root) {
        var image : ImageView = binding.image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemWriteDiaryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val context = holder.itemView.context
        Glide.with(context).load(imagePathArray[position]).into(holder.image)

    }

    override fun getItemCount(): Int = imagePathArray.size


}


package com.hansung.traveldiary.src.uploadDiary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemDiaryPictureBinding

class ViewPagerAdapter() : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    class PagerViewHolder(val binding : ItemDiaryPictureBinding) : RecyclerView.ViewHolder(binding.root) {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder{
        val binding = ItemDiaryPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {


    }

    override fun getItemCount(): Int = 1


}


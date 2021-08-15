package com.hansung.traveldiary.src.bulletin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemDiaryBinding
import com.hansung.traveldiary.databinding.ItemPopularSearchWordBinding
import com.hansung.traveldiary.src.travel.adapter.DiarySectionAdapter

class popularWordsAdapter(val popularWords:ArrayList<String>): RecyclerView.Adapter<popularWordsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemPopularSearchWordBinding) : RecyclerView.ViewHolder(binding.root) {
        val words=binding.keywords
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): popularWordsAdapter.ViewHolder {
        val binding= ItemPopularSearchWordBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return popularWordsAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: popularWordsAdapter.ViewHolder, position: Int) {
        holder.words.text=popularWords[position]
    }

    override fun getItemCount(): Int {
        return popularWords.size
    }


}
package com.hansung.traveldiary.src.bulletin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemPopularSearchWordBinding
import com.hansung.traveldiary.databinding.ItemRecentWordsBinding

class resentWordsAdapter(val resentWords:ArrayList<String>): RecyclerView.Adapter<resentWordsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemRecentWordsBinding) : RecyclerView.ViewHolder(binding.root) {
        val words=binding.keyword
        val day=binding.searchDays
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): resentWordsAdapter.ViewHolder {
        val binding= ItemRecentWordsBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return resentWordsAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: resentWordsAdapter.ViewHolder, position: Int) {
        holder.words.text=resentWords[position]
    }

    override fun getItemCount(): Int {
        return resentWords.size
    }
}
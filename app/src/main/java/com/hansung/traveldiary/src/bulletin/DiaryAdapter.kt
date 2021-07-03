package com.hansung.traveldiary.src.bulletin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.PostListBinding

class DiaryAdapter(private val viewModel:ArrayList<DiaryData>): RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: PostListBinding):RecyclerView.ViewHolder(binding.root){
        fun setContents(position: Int){
            with(viewModel[position]){
                binding.ivDiary.setImageResource(viewModel.get(position).Day_Picture)
                binding.tvDay.text="Day - ${day}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding= PostListBinding.inflate(layoutInflater,parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)

    }

    override fun getItemCount(): Int {
        return viewModel.size
    }
}
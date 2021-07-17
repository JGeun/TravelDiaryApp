package com.hansung.traveldiary.src.plan.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemDiarySectionBinding
import com.hansung.traveldiary.databinding.ItemWordSearchResultBinding
import com.hansung.traveldiary.src.bulletin.DiarySectionData
import com.hansung.traveldiary.src.plan.PlanAddDayActivity
import com.hansung.traveldiary.src.plan.TravelPlanActivity

class SearchWordResultAdapter(private val dataList:ArrayList<DiarySectionData>): RecyclerView.Adapter<SearchWordResultAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWordSearchResultBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding= ItemWordSearchResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount() = dataList.size
}
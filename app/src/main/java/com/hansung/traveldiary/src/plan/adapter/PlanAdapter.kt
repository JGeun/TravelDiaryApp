package com.hansung.traveldiary.src.plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemPlanBinding
import com.hansung.traveldiary.src.PlaceInfo


class PlanAdapter(private val planData : ArrayList<PlaceInfo>) : RecyclerView.Adapter<PlanAdapter.ViewHolder>(){
    class ViewHolder(val binding : ItemPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        val text : TextView = binding.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = planData[position].placeName
    }

    override fun getItemCount(): Int = planData.size
}
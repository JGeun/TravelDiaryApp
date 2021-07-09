package com.hansung.traveldiary.src.travel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.databinding.ItemTripplanBinding
import com.hansung.traveldiary.src.travel.PlanTripinData

class PlanTripAdapter(private val planTripinData: ArrayList<PlanTripinData>) :
    RecyclerView.Adapter<PlanTripAdapter.ViewHolder>() {
    private lateinit var binding : ItemTripplanBinding

    class ViewHolder(val binding : ItemTripplanBinding) : RecyclerView.ViewHolder(binding.root){
        val tripinImage : ImageView
        val tripinTitle : TextView

        init {
            tripinImage = binding.itemPlanImage
            tripinTitle = binding.itemPlanTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTripplanBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(planTripinData[position].image).apply(RequestOptions()).into(holder.tripinImage)
        holder.tripinTitle.text = planTripinData[position].title
    }

    override fun getItemCount() = planTripinData.size

}
package com.hansung.traveldiary.src.travel.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemTripplanBinding
import com.hansung.traveldiary.src.travel.PlanTripinData
import kotlin.properties.Delegates

class PlanTripAdapter(private val planTripinData: ArrayList<PlanTripinData>) :
    RecyclerView.Adapter<PlanTripAdapter.ViewHolder>() {
    private lateinit var binding : ItemTripplanBinding
    private lateinit var image : Drawable
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
        val data = planTripinData[position]
        if(data.color == "red"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_redbook, null)!!
        }else if(data.color == "blue"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_bluebook, null)!!
        }else if(data.color == "green"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_greenbook, null)!!
        }else if(data.color == "yellow"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_yellowbook, null)!!
        }else if(data.color == "gray"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_graybook, null)!!
        }else{
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_redbook, null)!!
        }
        Glide.with(holder.itemView.context).load(image).apply(RequestOptions()).into(holder.tripinImage)

        holder.tripinTitle.text = data.title
    }

    override fun getItemCount() = planTripinData.size
}
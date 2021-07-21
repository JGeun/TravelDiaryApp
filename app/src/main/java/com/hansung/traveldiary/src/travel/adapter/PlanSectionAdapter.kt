package com.hansung.traveldiary.src.travel.adapter

import android.content.Intent
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
import com.hansung.traveldiary.databinding.ItemPlanSectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.plan.add_day.PlanAddDayActivity
import com.hansung.traveldiary.src.travel.PlanSectionData

class PlanSectionAdapter(private val planTripinData: ArrayList<PlanSectionData>) :
    RecyclerView.Adapter<PlanSectionAdapter.ViewHolder>() {
    private lateinit var binding : ItemPlanSectionBinding
    private lateinit var image : Drawable
    class ViewHolder(val binding : ItemPlanSectionBinding) : RecyclerView.ViewHolder(binding.root){
        val tripinImage : ImageView
        val tripinTitle : TextView

        init {
            tripinImage = binding.itemPlanImage
            tripinTitle = binding.itemPlanTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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

        val context = holder.itemView.context
        holder.itemView.setOnClickListener{
            val intent = Intent(context, PlanAddDayActivity::class.java)
            intent.putExtra("title", data.title)
            (context as MainActivity).startActivity(intent)
        }
    }

    override fun getItemCount() = planTripinData.size
}
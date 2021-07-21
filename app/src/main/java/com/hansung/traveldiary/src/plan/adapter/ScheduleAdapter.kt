package com.hansung.traveldiary.src.plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemPlanBinding
import com.hansung.traveldiary.databinding.ItemScheduleBinding
import com.hansung.traveldiary.src.plan.ScheduleFragment
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import com.hansung.traveldiary.src.plan.model.PlaceData
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class ScheduleAdapter(private val placeViewModel : SharedPlaceViewModel) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>(){

    class ViewHolder(val binding : ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        val location : TextView = binding.itemScheduleLocation
        val deleteIcon : ImageView = binding.itemScheduleDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.location.text = placeViewModel.items[position].location
        holder.deleteIcon.setOnClickListener{
            placeViewModel.removePlace(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = placeViewModel.items.size
}
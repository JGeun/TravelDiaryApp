package com.hansung.traveldiary.src.travel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemTravellistBinding
import com.hansung.traveldiary.src.travel.PlanlistBottomDialog

class TravelListAdapter(private val regionData: ArrayList<String>) : RecyclerView.Adapter<TravelListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemTravellistBinding) : RecyclerView.ViewHolder(binding.root) {
        val region : TextView

        init {
            region = binding.regionName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTravellistBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = regionData.get(position)
        holder.region.text = data

        holder.itemView.setOnClickListener {
            Log.d("지역", "$data")
            setRegion(data)
        }
    }

    override fun getItemCount(): Int = regionData.size

    fun setRegion(region: String):String{
        return region
    }
}
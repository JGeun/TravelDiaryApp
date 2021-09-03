package com.hansung.traveldiary.src.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemSelectAreaBinding
import com.hansung.traveldiary.src.travel.AddBook.AreaViewModel
import com.hansung.traveldiary.src.travel.AddBook.SelectAreaAdapter
import com.hansung.traveldiary.src.travel.AddBook.SelectAreaBtmDialog

class AirPortAreaAdapter (private val regionData: ArrayList<String>, private var airportViewModel: AirportViewModel, private val btmDialog: AirPortBtmDialog, val isDestination:Boolean) : RecyclerView.Adapter<AirPortAreaAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemSelectAreaBinding) : RecyclerView.ViewHolder(binding.root) {
        val region : TextView

        init {
            region = binding.regionName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirPortAreaAdapter.ViewHolder {
        val binding = ItemSelectAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = regionData.size
    override fun onBindViewHolder(holder: AirPortAreaAdapter.ViewHolder, position: Int) {
        val data = regionData.get(position)
        holder.region.text = data

        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            if(!isDestination) {
                airportViewModel.setArea(data)
            }else{
                airportViewModel.setDestinationArea(data)
            }
            btmDialog.dismiss()
        }
    }
}
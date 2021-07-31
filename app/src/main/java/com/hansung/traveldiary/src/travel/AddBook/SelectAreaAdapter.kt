package com.hansung.traveldiary.src.travel.AddBook

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemSelectAreaBinding

class SelectAreaAdapter(private val regionData: ArrayList<String>, private var areaViewModel: AreaViewModel, private val btmDialog : SelectAreaBtmDialog) : RecyclerView.Adapter<SelectAreaAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemSelectAreaBinding) : RecyclerView.ViewHolder(binding.root) {
        val region : TextView

        init {
            region = binding.regionName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = regionData.get(position)
        holder.region.text = data

        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            areaViewModel.setArea(data)
            btmDialog.dismiss()
        }
    }

    override fun getItemCount(): Int = regionData.size
}
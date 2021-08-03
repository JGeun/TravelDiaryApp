package com.hansung.traveldiary.src.home.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemWeatherWeeklyBinding
import com.hansung.traveldiary.src.WeeklyWeatherData

class WeatherAdapter(private val weeklyList : ArrayList<WeeklyWeatherData>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemWeatherWeeklyBinding) : RecyclerView.ViewHolder(binding.root){
        val weekDate : TextView = binding.itemWeeklyDate
        val weekImage : ImageView = binding.itemWeeklyIcon
        val weekMin : TextView = binding.itemWeeklyMin
        val weekMax : TextView = binding.itemWeeklyMax
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeatherWeeklyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = weeklyList[position]
        val date = data.date.split("/")
        val month = date[0].toInt()
        val day = date[1].toInt()
        val weekDate = "${month}/${day}"
        holder.weekDate.text = weekDate
        Glide.with(holder.itemView.context).load(data.icon).into(holder.weekImage)
        holder.weekMin.text = data.min.toString()
        holder.weekMax.text = data.max.toString()
    }

    override fun getItemCount(): Int = weeklyList.size


}
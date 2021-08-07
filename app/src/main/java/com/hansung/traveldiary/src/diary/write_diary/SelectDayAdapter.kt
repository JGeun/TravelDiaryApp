package com.hansung.traveldiary.src.diary.write_diary

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemSelectDayBinding

class SelectDayAdapter(private val daysData: ArrayList<String>, private var viewModel: SelectDayViewModel, private val btmDialog: SelectDayBtmSheetFragment) : RecyclerView.Adapter<SelectDayAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSelectDayBinding) : RecyclerView.ViewHolder(binding.root) {
        val days : TextView

        init {
            days = binding.Days
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.days.text = daysData.get(position)
        holder.days.setOnClickListener{
            viewModel.setDay(position)
        }
    }

    override fun getItemCount(): Int = daysData.size
}
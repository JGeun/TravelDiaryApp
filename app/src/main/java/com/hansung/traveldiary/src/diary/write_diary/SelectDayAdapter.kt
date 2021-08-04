package com.hansung.traveldiary.src.diary.write_diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemSelectAreaBinding
import com.hansung.traveldiary.databinding.ItemSelectDayBinding
import com.hansung.traveldiary.src.travel.AddBook.AreaViewModel
import com.hansung.traveldiary.src.travel.AddBook.SelectAreaBtmDialog

class SelectDayAdapter(private val daysData: ArrayList<String>, private var areaViewModel: AreaViewModel, private val btmDialog: BottomSheetFragment) : RecyclerView.Adapter<SelectDayAdapter.ViewHolder>() {
    private lateinit var viewModel:WriteViewModel

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
        viewModel = WriteViewModel()
        val data = daysData.get(position)
        holder.days.text = data

        val context = holder.itemView.context

        holder.itemView.setOnClickListener {

            println(viewModel.data.value)
            viewModel.data.value=position

        }
    }

    override fun getItemCount(): Int = daysData.size
}
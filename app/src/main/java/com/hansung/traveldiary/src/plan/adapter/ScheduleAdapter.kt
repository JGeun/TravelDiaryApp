package com.hansung.traveldiary.src.plan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemScheduleBinding
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class ScheduleAdapter(private val placeViewModel : SharedPlaceViewModel) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>(){

    class ViewHolder(val binding : ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        val location : TextView = binding.itemScheduleLocation
        val deleteIcon : ImageView = binding.itemScheduleDelete
        val topBar : View = binding.topBar
        val bottomBar : View = binding.bottomBar
        val dotImg : ImageView = binding.dotImg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context= holder.itemView.context
        val barColor = when((context as TravelPlanBaseActivity).getColor()){
            "pink" -> R.color.pink
            "purple" -> R.color.purple
            "yellow" -> R.color.yellow
            "sky" -> R.color.sky
            "blue" -> R.color.blue
            "orange" -> R.color.orange
            else -> R.color.orange
        }
        holder.topBar.setBackgroundColor(context.resources.getColor(barColor))
        holder.bottomBar.setBackgroundColor(context.resources.getColor(barColor))

        val  dotColor = when((context).getColor()){
            "pink" -> R.drawable.pink_dot
            "purple" -> R.drawable.purple_dot
            "yellow" -> R.drawable.yellow_dot
            "sky" -> R.drawable.sky_dot
            "blue" -> R.drawable.blue_dot
            "orange" -> R.drawable.orange_dot
            else -> R.drawable.orange_dot
        }
        holder.dotImg.setBackgroundResource(dotColor)


        holder.location.text = placeViewModel.items.dayPlaceList[TravelPlanBaseActivity.index].placeInfoArray[position].placeName
        holder.deleteIcon.setOnClickListener{
            placeViewModel.removePlace(TravelPlanBaseActivity.index, position)
            notifyDataSetChanged()
        }
        Log.d("리스트", placeViewModel.items.dayPlaceList[TravelPlanBaseActivity.index].placeInfoArray.size.toString())
        if(position==0)
            holder.topBar.visibility = View.INVISIBLE
        if(position==placeViewModel.items.dayPlaceList[TravelPlanBaseActivity.index].placeInfoArray.size-1)
            holder.bottomBar.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int = placeViewModel.items.dayPlaceList[TravelPlanBaseActivity.index].placeInfoArray.size
}
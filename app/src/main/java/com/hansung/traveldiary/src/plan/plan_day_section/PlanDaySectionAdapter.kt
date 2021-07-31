package com.hansung.traveldiary.src.plan.plan_day_section

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemPlanDaySectionBinding
import com.hansung.traveldiary.src.PlaceInfoFolder
import com.hansung.traveldiary.src.PlanData
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import java.text.SimpleDateFormat
import java.util.*


class PlanDaySectionAdapter(val placeInfoFolder: PlaceInfoFolder): RecyclerView.Adapter<PlanDaySectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPlanDaySectionBinding): RecyclerView.ViewHolder(binding.root){
        val dayText = binding.itemAdpDayCount
        val date = binding.itemAdpDate
        val map = binding.itemAdpMap
        val schedule = binding.itemAdpSchedule
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemPlanDaySectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context= holder.itemView.context
        val dayCountText = "Day - " + (position+1).toString()
        holder.dayText.text = dayCountText
        val date = placeInfoFolder.dayPlaceList[position].date
        holder.date.text = date
        val day = getDateDay(date)
        println("date: " + date)
        println("day: " + day)

        val intent = Intent(context, TravelPlanBaseActivity::class.java)
        intent.putExtra("title", (context as PlanDaySectionActivity).getTitleContents())
        holder.map.setOnClickListener{
            intent.putExtra("menu", "map")
            intent.putExtra("index", position)
            (context as PlanDaySectionActivity).startActivity(intent)
        }
        holder.schedule.setOnClickListener{
            intent.putExtra("menu", "schedule")
            intent.putExtra("index", position)
            (context as PlanDaySectionActivity).startActivity(intent)
        }

//        holder.itemView.setOnClickListener{
//
//        }
    }

    override fun getItemCount() = placeInfoFolder.dayPlaceList.size

    fun getDateDay(date : String) : String {

        var day = "";
        val dateFormat = SimpleDateFormat("yyyy-MM-dd");
        val nDate: Date = dateFormat.parse(date);

        val cal: Calendar = Calendar.getInstance();
        cal.time = nDate;
        println("cal: " + cal.get(Calendar.DAY_OF_WEEK))
        when (cal.get(Calendar.DAY_OF_WEEK)) {
            1 -> day = "일";
            2 -> day = "월";
            3 -> day = "화";
            4 -> day = "수";
            5 -> day = "목";
            6 -> day = "금";
            7 -> day = "토";
        }

        return day;
    }

}
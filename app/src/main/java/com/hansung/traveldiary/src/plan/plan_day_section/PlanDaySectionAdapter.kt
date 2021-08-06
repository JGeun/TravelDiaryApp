package com.hansung.traveldiary.src.plan.plan_day_section

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemPlanDaySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.PlaceInfoFolder2
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import java.text.SimpleDateFormat
import java.util.*


class PlanDaySectionAdapter(val index: Int): RecyclerView.Adapter<PlanDaySectionAdapter.ViewHolder>() {
    private val color = MainActivity.userPlanArray[index].planBaseData.color
    inner class ViewHolder(private val binding: ItemPlanDaySectionBinding): RecyclerView.ViewHolder(binding.root){
        val dayText = binding.itemAdpDayCount
        val date = binding.itemAdpDate
        val map = binding.itemAdpMap
        val bar = binding.itemBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemPlanDaySectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context= holder.itemView.context
        val dayCountText = (position+1).toString()+"일차 여행 코스"
        holder.dayText.text = dayCountText
        val date = afterDate(MainActivity.userPlanArray[index].planBaseData.startDate, position)
        holder.date.text = date
        val day = getDateDay(date)

        val barColor = when(color){
            "pink" -> R.color.pink
            "purple" -> R.color.purple
            "yellow" -> R.color.yellow
            "sky" -> R.color.sky
            "blue" -> R.color.blue
            "orange" -> R.color.orange
            else -> R.color.orange
        }
        holder.bar.setBackgroundColor(ResourcesCompat.getColor(context.resources, barColor, null))

        val intent = Intent(context, TravelPlanBaseActivity::class.java)
        intent.putExtra("title", MainActivity.userPlanArray[index].planBaseData.title)
        holder.map.setOnClickListener{
            intent.putExtra("menu", "map")
            intent.putExtra("index", position)
            intent.putExtra("color", color)
            context.startActivity(intent)
        }
        holder.itemView.setOnClickListener{
            intent.putExtra("menu", "schedule")
            intent.putExtra("index", position)
            intent.putExtra("color", color)
            context.startActivity(intent)
        }

//        holder.itemView.setOnClickListener{
//
//        }
    }

    override fun getItemCount() = MainActivity.userPlanArray[index].placeArray.size

    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }

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
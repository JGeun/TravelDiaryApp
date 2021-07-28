package com.hansung.traveldiary.src.travel.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemPlanSectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.PlanBookData
import com.hansung.traveldiary.src.plan.plan_day_section.PlanDaySectionActivity

class PlanSectionAdapter(val planBookList: ArrayList<PlanBookData>) :
    RecyclerView.Adapter<PlanSectionAdapter.ViewHolder>() {
    private lateinit var binding : ItemPlanSectionBinding
    private lateinit var image : Drawable
    private val monthUnit = arrayListOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

//    fun deleteItem(position: Int){
//        planBookList.removeAt(position)
//        notifyDataSetChanged()
//    }

    class ViewHolder(val binding : ItemPlanSectionBinding) : RecyclerView.ViewHolder(binding.root){
        val planSectionImage : ImageView
        val planSectionTitle : TextView
        var planSectionStartMonth : TextView
        var planSectionEndMonth : TextView
        var planSectionStartDate : TextView
        var planSectionEndDate : TextView

        init {
            planSectionImage = binding.itemPlanImage
            planSectionTitle = binding.itemPlanTitle
            planSectionStartMonth = binding.itemStartMonth
            planSectionEndMonth = binding.itemEndMonth
            planSectionStartDate = binding.itemStartDate
            planSectionEndDate = binding.itemEndDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = planBookList[position]
        val color = data.planTotalData.color
        if(color == "blue"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_blue, null)!!
        }else if(color == "green"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_green, null)!!
        }else if(color == "olive"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_olive, null)!!
        }else if(color == "pink"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_pink, null)!!
        }else if(color == "purple"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_purple, null)!!
        }else{
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_redbook, null)!!
        }
        Glide.with(holder.itemView.context).load(image).apply(RequestOptions()).into(holder.planSectionImage)
        holder.planSectionTitle.text = data.title
        val startMonth = data.planTotalData.startDate.substring(5, 7).toInt()
        val endMonth = data.planTotalData.endDate.substring(5, 7).toInt()
        holder.planSectionStartMonth.text = monthUnit[startMonth-1]
        holder.planSectionEndMonth.text = monthUnit[endMonth-1]
        holder.planSectionStartDate.text = data.planTotalData.startDate.substring(8, 10)
        holder.planSectionEndDate.text = data.planTotalData.endDate.substring(8, 10)

        val context = holder.itemView.context
        holder.itemView.setOnClickListener{
            val intent = Intent(context, PlanDaySectionActivity::class.java)
            intent.putExtra("title", data.title)
            intent.putExtra("pos", position)
            (context as MainActivity).startActivity(intent)
        }
    }

    override fun getItemCount() = planBookList.size

}
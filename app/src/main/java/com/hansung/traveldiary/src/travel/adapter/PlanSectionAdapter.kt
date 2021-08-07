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
import com.hansung.traveldiary.config.DeleteBottomDialogFragment
import com.hansung.traveldiary.databinding.ItemPlanSectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserPlanData
import com.hansung.traveldiary.src.plan.plan_day_section.PlanDaySectionActivity

class PlanSectionAdapter(val userPlanData: ArrayList<UserPlanData>) :
    RecyclerView.Adapter<PlanSectionAdapter.ViewHolder>() {
    private lateinit var binding : ItemPlanSectionBinding
    private lateinit var image : Drawable
    private val monthUnit = arrayListOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    class ViewHolder(val binding : ItemPlanSectionBinding) : RecyclerView.ViewHolder(binding.root){
        val planSectionImage : ImageView
        val planSectionTitle : TextView
        var planSectionStartMonth : TextView
        var planSectionEndMonth : TextView
        var planSectionStartDate : TextView
        var planSectionEndDate : TextView
        var planSectionItemCheck : ImageView

        init {
            planSectionImage = binding.itemPlanImage
            planSectionTitle = binding.itemPlanTitle
            planSectionStartMonth = binding.itemStartMonth
            planSectionEndMonth = binding.itemEndMonth
            planSectionStartDate = binding.itemStartDate
            planSectionEndDate = binding.itemEndDate
            planSectionItemCheck = binding.itemCheck
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = userPlanData[position]
        val baseData = data.baseData
        val color = baseData.color
        if(color == "blue"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_blue, null)!!
        }else if(color == "yellow"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_yellow, null)!!
        }else if(color == "orange"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_orange, null)!!
        }else if(color == "pink"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_pink, null)!!
        }else if(color == "purple"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_purple, null)!!
        }else if(color == "sky"){
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_sky, null)!!
        }else{
            image = ResourcesCompat.getDrawable(holder.itemView.resources, R.drawable.ic_diary_blue, null)!!
        }
        Glide.with(holder.itemView.context).load(image).apply(RequestOptions()).into(holder.planSectionImage)
        holder.planSectionTitle.text = data.baseData.title
        val startMonth = baseData.startDate.substring(5, 7).toInt()
        val endMonth = baseData.endDate.substring(5, 7).toInt()
        holder.planSectionStartMonth.text = monthUnit[startMonth-1]
        holder.planSectionEndMonth.text = monthUnit[endMonth-1]
        holder.planSectionStartDate.text = baseData.startDate.substring(8, 10)
        holder.planSectionEndDate.text = baseData.endDate.substring(8, 10)

        val context = holder.itemView.context
        holder.itemView.setOnClickListener{
            val intent = Intent(context, PlanDaySectionActivity::class.java)
            intent.putExtra("index", position)
            (context as MainActivity).startActivity(intent)
        }

        holder.planSectionItemCheck.setOnClickListener{
            val deleteBtmSheetDialogFragment = DeleteBottomDialogFragment{
                when(it){
                    0 -> {
                        (context as MainActivity).updatePlanBook(position, true)
                    }
                    1 -> {
                        (context as MainActivity).removePlanBook(position)
                    }
                }
            }
            deleteBtmSheetDialogFragment.show((context as MainActivity).supportFragmentManager, deleteBtmSheetDialogFragment.tag)
        }
    }

    override fun getItemCount() = userPlanData.size

}
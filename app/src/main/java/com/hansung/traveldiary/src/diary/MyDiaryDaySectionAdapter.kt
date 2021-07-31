package com.hansung.traveldiary.src.diary

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemBulletinDaySectionBinding
import com.hansung.traveldiary.src.bulletin.DiarySectionData
import com.hansung.traveldiary.src.uploadDiary.MakeDiaryActivity

class MyDiaryDaySectionAdapter(private val dataList:ArrayList<DiarySectionData>): RecyclerView.Adapter<MyDiaryDaySectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemBulletinDaySectionBinding): RecyclerView.ViewHolder(binding.root){
        val dayText = binding.dsItemTvDayCount
        val tag = binding.dsItemTvTag
        val diaryImage = binding.dsItemIvDiary
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemBulletinDaySectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.dayText.text = (position+1).toString()
        holder.tag.text = data.tag
        val context = holder.itemView.context
        Glide.with(holder.itemView.context).load(data.diaryImage).into(holder.diaryImage)
        holder.itemView.setOnClickListener{
            context.startActivity(Intent(context, ShowDiaryActivity::class.java))
        }
    }

    override fun getItemCount() = dataList.size
}
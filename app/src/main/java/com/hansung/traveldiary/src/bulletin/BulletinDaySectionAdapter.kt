package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemBulletinDaySectionBinding
import com.hansung.traveldiary.src.DiaryBulletinData

class BulletinDaySectionAdapter(private val diaryAllList:ArrayList<DiaryBulletinData>, private val index: Int): RecyclerView.Adapter<BulletinDaySectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemBulletinDaySectionBinding):RecyclerView.ViewHolder(binding.root){
        val dayText = binding.itemBdsDayCount
//        val tag = binding.dsItemTvTag
        val diaryImage = binding.itemBdsIvDiary
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding= ItemBulletinDaySectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = diaryAllList[index].diaryData
        holder.dayText.text = (position+1).toString()
//        holder.tag.text = data.tag
        val context = holder.itemView.context
        Glide.with(context).load(data.diaryBaseData.mainImage).into(holder.diaryImage)
        val intent = Intent(context, ShowDiaryActivity::class.java)
        intent.putExtra("index", index)
        intent.putExtra("day", position)
        holder.itemView.setOnClickListener{
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = diaryAllList[index].diaryData.diaryInfoFolder.diaryDayFolder.size
}
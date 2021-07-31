package com.hansung.traveldiary.src.travel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemLasttripBinding
import com.hansung.traveldiary.src.DiaryBulletinData
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionActivity

class TravelDiarySectionAdapter(val myDiaryList : ArrayList<DiaryBulletinData>):RecyclerView.Adapter<TravelDiarySectionAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemLasttripBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.btItemTvTitle
        val thumbnail = binding.btItemIvThumbnail
        val hashtag = binding.btItemTvTag
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelDiarySectionAdapter.ViewHolder {
        val binding= ItemLasttripBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelDiarySectionAdapter.ViewHolder, position: Int) {
        val data = myDiaryList[position]
        holder.title.text = data.diaryData.diaryBaseData.title
//        holder.hashtag.text = data.tv_tag
        Glide.with(holder.itemView.context).load(data.diaryData.diaryBaseData.mainImage).into(holder.thumbnail)

        holder.itemView.setTag(position)

        val context = holder.itemView.context
        holder.itemView.setOnClickListener{
            val intent = Intent(context, MyDiaryDaySectionActivity::class.java)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = myDiaryList.size

}
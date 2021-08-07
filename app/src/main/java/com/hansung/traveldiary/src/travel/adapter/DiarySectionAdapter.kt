package com.hansung.traveldiary.src.travel.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemLasttripBinding
import com.hansung.traveldiary.src.DiaryBulletinData
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionActivity

class DiarySectionAdapter():RecyclerView.Adapter<DiarySectionAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemLasttripBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.btItemTvTitle
        val thumbnail = binding.btItemIvThumbnail
        val hashtag = binding.btItemTvTag
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiarySectionAdapter.ViewHolder {
        val binding= ItemLasttripBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiarySectionAdapter.ViewHolder, position: Int) {
        val data = MainActivity.userDiaryArray[position]
        val context = holder.itemView.context
        holder.title.text = data.baseData.title
//        holder.hashtag.text = data.tv_tag
        val imagePath = data.baseData.mainImage
        if(imagePath != "")
            Glide.with(holder.itemView.context).load(imagePath).into(holder.thumbnail)
        else{
            Glide.with(holder.itemView.context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_no_main_image, null)).into(holder.thumbnail)
        }

        holder.itemView.setTag(position)


        holder.itemView.setOnClickListener{
            val intent = Intent(context, MyDiaryDaySectionActivity::class.java)
            intent.putExtra("index", position)
            Log.d("과정", "DiarySectionAdapter Index: $position")
            Log.d("과정", "진짜title: ${MainActivity.diaryTitleList.titleFolder[position]}")
            Log.d("과정", "플랜title: ${MainActivity.myDiaryList[position].diaryData.diaryBaseData.title}")
            context.startActivity(intent)
            (context as MainActivity).overridePendingTransition(0, 0)
        }
    }

    override fun getItemCount(): Int = MainActivity.userDiaryArray.size

}
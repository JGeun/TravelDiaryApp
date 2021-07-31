package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemBulletinBinding
import com.hansung.traveldiary.src.DiaryBulletinData

class BulletinAdapter(private val diaryAllData: ArrayList<DiaryBulletinData>):RecyclerView.Adapter<BulletinAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemBulletinBinding):RecyclerView.ViewHolder(binding.root){
        val title = binding.btItemTvTitle
        val thumbnail = binding.btItemIvThumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding= ItemBulletinBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = diaryAllData[position].diaryData
        holder.title.text = data.diaryBaseData.title
        Glide.with(holder.itemView.context).load(data.diaryBaseData.mainImage).into(holder.thumbnail)

        holder.itemView.setTag(position)

        val context = holder.itemView.context

        holder.itemView.setOnClickListener{
            val intent = Intent(context,BulletinDaySectionActivity::class.java)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = diaryAllData.size

}
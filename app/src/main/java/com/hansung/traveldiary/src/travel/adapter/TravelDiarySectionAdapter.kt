package com.hansung.traveldiary.src.travel.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemLasttripBinding
import com.hansung.traveldiary.src.travel.LastTripData
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionActivity

class TravelDiarySectionAdapter(val lasttripList: ArrayList<LastTripData>):RecyclerView.Adapter<TravelDiarySectionAdapter.ViewHolder>() {
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
        val data = lasttripList.get(position)
        holder.title.text = data.tv_title
        holder.hashtag.text = data.tv_tag
        Glide.with(holder.itemView.context).load(data.iv_profile).into(holder.thumbnail)

        holder.itemView.setTag(position)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView?.context, MyDiaryDaySectionActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent,null)
        }
    }

    override fun getItemCount(): Int = lasttripList.size

}
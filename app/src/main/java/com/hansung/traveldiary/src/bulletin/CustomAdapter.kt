package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemBulletinBinding

class CustomAdapter(private val bulletinList: ArrayList<BulletinData>):RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemBulletinBinding):RecyclerView.ViewHolder(binding.root){
        val title = binding.btItemTvTitle
        val thumbnail = binding.btItemIvThumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding= ItemBulletinBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = bulletinList.get(position)
        holder.title.text = data.tv_Contents
        Glide.with(holder.itemView.context).load(data.iv_profile).into(holder.thumbnail)

        holder.itemView.setTag(position)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView?.context,DiarySectionActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent,null)
        }
    }

    override fun getItemCount(): Int = bulletinList.size

}
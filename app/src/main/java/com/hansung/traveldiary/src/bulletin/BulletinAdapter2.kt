package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletin2Binding
import com.hansung.traveldiary.src.DiaryBulletinData

class BulletinAdapter2(private val diaryAllData: ArrayList<DiaryBulletinData>):RecyclerView.Adapter<BulletinAdapter2.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemBulletin2Binding):RecyclerView.ViewHolder(binding.root){
        val userImage = binding.btItemUserImage
        val userName = binding.btItemUserName
        val title = binding.btItemTvTitle
        val thumbnail = binding.btItemIvThumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding= ItemBulletin2Binding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val data = diaryAllData[position].diaryData

//        Glide.with(context).load(data.diaryBaseData.userImage).into(holder.userImage)
        Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_beach, null)).circleCrop().into(holder.userImage)
        holder.userName.text = data.diaryBaseData.userName
        holder.title.text = data.diaryBaseData.title
        Glide.with(context).load(data.diaryBaseData.mainImage).into(holder.thumbnail)

        holder.itemView.setTag(position)



        holder.itemView.setOnClickListener{
            val intent = Intent(context,BulletinDaySectionActivity::class.java)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = diaryAllData.size

}
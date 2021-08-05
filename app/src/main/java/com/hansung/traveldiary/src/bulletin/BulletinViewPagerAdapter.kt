package com.hansung.traveldiary.src.bulletin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletinImageBinding
import com.hansung.traveldiary.src.DiaryBulletinData

class BulletinViewPagerAdapter(private val diaryAllData: ArrayList<DiaryBulletinData>): RecyclerView.Adapter<BulletinViewPagerAdapter.PagerViewHolder>() {

    class PagerViewHolder(val binding : ItemBulletinImageBinding) : RecyclerView.ViewHolder(binding.root) {
        var image : ImageView = binding.image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemBulletinImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //binding.image.setImageResource(R.drawable.bg_profile)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val data = diaryAllData[position].diaryData
        val context = holder.itemView.context
        val url=data.diaryBaseData.mainImage
        Glide.with(context).load(url).apply(RequestOptions().centerCrop()).into(holder.image)
        //Glide.with(context).load(imagePathArray[position]).into(holder.image)

    }

    override fun getItemCount(): Int = diaryAllData.size
}
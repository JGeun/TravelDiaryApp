package com.hansung.traveldiary.src.bulletin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletinImageBinding

class BulletinViewPagerAdapter(val imagePathArray: MutableList<Int>): RecyclerView.Adapter<BulletinViewPagerAdapter.PagerViewHolder>() {
    class PagerViewHolder(val binding : ItemBulletinImageBinding) : RecyclerView.ViewHolder(binding.root) {
        var image : ImageView = binding.image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemBulletinImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.image.setImageResource(R.drawable.bg_profile)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val context = holder.itemView.context
        //Glide.with(context).load(imagePathArray[position]).into(holder.image)

    }

    override fun getItemCount(): Int = imagePathArray.size
    inner class BulletinViewPagerAdapter(itemView: View):RecyclerView.ViewHolder(itemView){

    }
}
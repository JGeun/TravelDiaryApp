package com.hansung.traveldiary.src.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletinBinding
import com.hansung.traveldiary.databinding.ItemPopularPlaceBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.bulletin.BulletinAdapter
import com.hansung.traveldiary.src.bulletin.BulletinDaySectionActivity
import com.hansung.traveldiary.src.bulletin.BulletinViewPagerAdapter
import com.hansung.traveldiary.src.bulletin.OtherUserActivity
import com.hansung.traveldiary.src.diary.CommentListActivity
import com.hansung.traveldiary.src.home.RecommandActivity

class PopularPlaceAdapter (val popularPlace:ArrayList<RecommandActivity.place>): RecyclerView.Adapter<PopularPlaceAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemPopularPlaceBinding): RecyclerView.ViewHolder(binding.root){
        val placeName=binding.placeName
        val placeAddress = binding.placeAddress
        val placeWebsite= binding.placeWebsite
        val cal= binding.placeCal
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val binding= ItemPopularPlaceBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placeName.text = popularPlace[position].placeName
        holder.placeAddress.text = popularPlace[position].placeAddress
        holder.placeWebsite.text = popularPlace[position].placeURL
        holder.cal.text = popularPlace[position].placeCal
    }

    override fun getItemCount() = popularPlace.size
}
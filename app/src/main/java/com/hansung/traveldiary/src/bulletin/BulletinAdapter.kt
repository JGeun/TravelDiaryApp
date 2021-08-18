package com.hansung.traveldiary.src.bulletin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletinBinding
import com.hansung.traveldiary.src.MainActivity

class BulletinAdapter():RecyclerView.Adapter<BulletinAdapter.ViewHolder>() {
    private val db = Firebase.firestore

    inner class ViewHolder(binding: ItemBulletinBinding):RecyclerView.ViewHolder(binding.root){
        val userImage = binding.btItemUserImage
        val userName = binding.btItemUserName
        val likeCnt=binding.btItemTvLikecnt
        val comment=binding.btItemTvComment
        val viewpager=binding.viewPager
        var index=-1
        var url=""
        //val thumbnail = binding.btItemIvThumbnail
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val binding= ItemBulletinBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val data = MainActivity.bulletinDiaryArray[position].userDiaryData

        holder.userName.text = MainActivity.bulletinDiaryArray[position].userInfo.nickname
        val userImagePath = MainActivity.bulletinDiaryArray[position].userInfo.profileImage
        if(userImagePath == "")
            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_beach, null)).circleCrop().into(holder.userImage)
        else
            Glide.with(context).load(userImagePath.toString()).circleCrop().into(holder.userImage)

        holder.viewpager.adapter = BulletinViewPagerAdapter(position)
        holder.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //Glide.with(context).load(data.diaryBaseData.mainImage).into(holder.thumbnail)
        holder.likeCnt.text=data.baseData.like.toString()
        holder.comment.text=data.baseData.comments.toString()
        holder.itemView.setTag(position)
        holder.url=userImagePath.toString()

        holder.itemView.setOnClickListener{
            val intent = Intent(context,BulletinDaySectionActivity::class.java)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }

        holder.userImage.setOnClickListener {
            val intent = Intent(context,OtherUserActivity::class.java)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = MainActivity.bulletinDiaryArray.size
}
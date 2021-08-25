package com.hansung.traveldiary.src.bulletin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletinBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.diary.CommentListActivity

class BulletinAdapter():RecyclerView.Adapter<BulletinAdapter.ViewHolder>() {
    private val user = Firebase.auth.currentUser
    private val db = Firebase.firestore
    private var chk_like=false
    inner class ViewHolder(binding: ItemBulletinBinding):RecyclerView.ViewHolder(binding.root){
        val userImage = binding.btItemUserImage
        val userName = binding.btItemUserName
        val ivLike=binding.btItemIvLike
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
        println("BUlletinAdapter에 들어옴")
        println(MainActivity.bulletinDiaryArray.size)
        holder.userName.text = MainActivity.bulletinDiaryArray[position].userInfo.nickname
        val userImagePath = MainActivity.bulletinDiaryArray[position].userInfo.profileImage
        if(userImagePath == "")
            Glide.with(context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_beach, null)).circleCrop().into(holder.userImage)
        else
            Glide.with(context).load(userImagePath.toString()).circleCrop().into(holder.userImage)

        holder.viewpager.adapter = BulletinViewPagerAdapter(position)
        holder.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //Glide.with(context).load(data.diaryBaseData.mainImage).into(holder.thumbnail)
        holder.likeCnt.text=data.baseData.like.likeUserFolder.size.toString()
        holder.comment.text=data.baseData.comments.commentsFolder.size.toString()
        holder.itemView.setTag(position)
        holder.url=userImagePath.toString()
        holder.itemView.setOnClickListener{
            val intent = Intent(context,BulletinDaySectionActivity::class.java)
            Log.d("체크", "Email: ${MainActivity.bulletinDiaryArray[position].userDiaryData.baseData.userEmail}")
            intent.putExtra("index", position)
            context.startActivity(intent)
        }

        for(i in 0 until MainActivity.bulletinDiaryArray.size){
            println("BulletinAdapter의 유저 이메일은 "+MainActivity.bulletinDiaryArray[position].userInfo.email.toString())
        }

        holder.userImage.setOnClickListener {
            val intent = Intent(context,OtherUserActivity::class.java)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }

        holder.comment.setOnClickListener {
            val intent= Intent(context,CommentListActivity::class.java)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = MainActivity.bulletinDiaryArray.size
}
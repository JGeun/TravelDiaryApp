package com.hansung.traveldiary.src.bulletin

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletinBinding
import com.hansung.traveldiary.src.BulletinData
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.diary.CommentListActivity

class UserNameAdapter() : RecyclerView.Adapter<UserNameAdapter.ViewHolder>() {
    private val items:ArrayList<BulletinData> = ArrayList()
    private val db = Firebase.firestore
    private val viewModel=SearchWordVIewModel()
    inner class ViewHolder(binding: ItemBulletinBinding): RecyclerView.ViewHolder(binding.root){
        val layout=binding.layout
        val userImage = binding.btItemUserImage
        val userName = binding.btItemUserName
        val likeCnt=binding.btItemTvLikecnt
        val comment=binding.btItemTvComment
        val viewpager=binding.viewPager
        var index=-1
        var url=""
        //val thumbnail = binding.btItemIvThumbnail
    }
    private var count=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val binding= ItemBulletinBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val data = items[position].userDiaryData
        println(items[position].userInfo.nickname)
        println(items[position].userDiaryData.baseData.title)
        holder.userName.text = items[position].userInfo.nickname

        val userImagePath =items[position].userInfo.profileImage
        if (userImagePath == "")
            Glide.with(context).load(
                ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.img_beach,
                    null
                )
            ).circleCrop().into(holder.userImage)
        else
            Glide.with(context).load(userImagePath).circleCrop()
                .into(holder.userImage)
        var index=0
        for(i in 0 until MainActivity.bulletinDiaryArray.size){
            if(MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.idx == data.baseData.idx){
                index= i
                break
            }
        }
        holder.viewpager.adapter = BulletinViewPagerAdapter(index)
        holder.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //Glide.with(context).load(data.diaryBaseData.mainImage).into(holder.thumbnail)
        holder.likeCnt.text = data.baseData.like.likeUserFolder.size.toString()
        holder.comment.text = data.baseData.comments.commentsFolder.size.toString()
        holder.itemView.setTag(position)
        holder.url = userImagePath.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BulletinDaySectionActivity::class.java)
            intent.putExtra("index", index)
            context.startActivity(intent)
        }

        holder.userImage.setOnClickListener {
            val intent = Intent(context, OtherUserActivity::class.java)
            intent.putExtra("index", index)
            context.startActivity(intent)
        }

        holder.comment.setOnClickListener {
            val intent = Intent(context, CommentListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    fun add(data : BulletinData){
        items.add(data)
    }

    fun clear(){
        items.clear()
    }
}









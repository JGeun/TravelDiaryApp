package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemBulletinDaySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserDiaryData

class BulletinDaySectionAdapter(private val index: Int, private val size: Int,private val userImagePath:String): RecyclerView.Adapter<BulletinDaySectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemBulletinDaySectionBinding):RecyclerView.ViewHolder(binding.root){
        val dayText = binding.itemBdsDayCount
        //        val tag = binding.dsItemTvTag
        val diaryImage = binding.itemBdsIvDiary
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding= ItemBulletinDaySectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = MainActivity.bulletinDiaryArray[index].userDiaryData.diaryArray[position]
        //diaryAllList[index].diaryData
        holder.dayText.text = (position+1).toString()
//        holder.tag.text = data.tag
        val context = holder.itemView.context
        println("현재 게시글을 작성한 유저의 이메일 : "+MainActivity.userList.emailFolder[index].toString())
        if (data.diaryInfo.imagePathArray.size != 0) {
            Glide.with(holder.itemView.context).load(data.diaryInfo.imagePathArray[0])
                .into(holder.diaryImage)
        } else {
            Glide.with(holder.itemView.context).load(
                ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.img_no_main_image,
                    null
                )
            ).into(holder.diaryImage)
        }

        holder.itemView.setOnClickListener{
            println("index: ${index} day: ${position}")
            println(MainActivity.bulletinDiaryArray[index].userDiaryData.diaryArray[position].diaryInfo.diaryTitle)
            val intent = Intent(context, ShowDiaryActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("day", position)
            intent.putExtra("isBulletin", true)
            intent.putExtra("image",userImagePath)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = size
}
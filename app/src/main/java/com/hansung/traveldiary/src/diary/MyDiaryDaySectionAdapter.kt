package com.hansung.traveldiary.src.diary

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemDiaryDaySectionBinding
import com.hansung.traveldiary.src.DiaryBulletinData
import com.hansung.traveldiary.src.bulletin.ShowDiaryActivity
import com.hansung.traveldiary.src.diary.write_diary.WriteDiaryActivity

class MyDiaryDaySectionAdapter(private val myDiaryList:ArrayList<DiaryBulletinData>, private val index : Int): RecyclerView.Adapter<MyDiaryDaySectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDiaryDaySectionBinding): RecyclerView.ViewHolder(binding.root){
        val dayText = binding.dsItemTvDayCount
//        val tag = binding.dsItemTvTag
        val diaryImage = binding.dsItemIvDiary
        val showImage = binding.show
        val writeImage = binding.write
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemDiaryDaySectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = myDiaryList[index].diaryData
        holder.dayText.text = (position+1).toString()
//        holder.tag.text = data.tag
        val context = holder.itemView.context
        Glide.with(holder.itemView.context).load(data.diaryBaseData.mainImage).into(holder.diaryImage)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ShowDiaryActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("day", position)
            context.startActivity(Intent(context, ShowDiaryActivity::class.java))
        }
        holder.showImage.setOnClickListener{
            val intent = Intent(context, ShowDiaryActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("day", position)
            context.startActivity(Intent(context, ShowDiaryActivity::class.java))
        }

        holder.writeImage.setOnClickListener{
            val intent = Intent(context, ShowDiaryActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("day", position)
            context.startActivity(Intent(context, WriteDiaryActivity::class.java))
        }
    }

    override fun getItemCount() = myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList.size
}
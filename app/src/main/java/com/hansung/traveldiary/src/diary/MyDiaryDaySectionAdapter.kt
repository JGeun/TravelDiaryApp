package com.hansung.traveldiary.src.diary

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ItemDiaryDaySectionBinding
import com.hansung.traveldiary.src.DiaryBulletinData
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.bulletin.ShowDiaryActivity
import com.hansung.traveldiary.src.diary.write_diary.WriteDiaryActivity

class MyDiaryDaySectionAdapter(private val index: Int, private var viewModel: DiaryDayViewModel) :
    RecyclerView.Adapter<MyDiaryDaySectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDiaryDaySectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val dayText = binding.dsItemTvDayCount
        val diaryImage = binding.dsItemIvDiary
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDiaryDaySectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = MainActivity.userDiaryArray[index].diaryArray[position]
        holder.dayText.text = (position + 1).toString()
//        holder.tag.text = data.tag
        val context = holder.itemView.context

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


        holder.itemView.setOnClickListener {
            val intent: Intent
            val showImage = R.drawable.ic_find_black
            if (viewModel.imageData.value == showImage) {
                println("다이어리 보기")
                intent = Intent(context, ShowDiaryActivity::class.java)
            } else {
                println("다이어리 작성")
                intent = Intent(context, WriteDiaryActivity::class.java)
            }
            intent.putExtra("index", index)
            intent.putExtra("day", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = MainActivity.userDiaryArray[index].diaryArray.size
}
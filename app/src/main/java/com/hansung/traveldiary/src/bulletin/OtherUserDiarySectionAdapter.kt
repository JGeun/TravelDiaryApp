package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.config.DeleteBottomDialogFragment
import com.hansung.traveldiary.databinding.ItemBulletinDaySectionBinding
import com.hansung.traveldiary.databinding.ItemDiaryBinding
import com.hansung.traveldiary.databinding.ItemDiaryDaySectionBinding
import com.hansung.traveldiary.databinding.ItemOtherUserDiaryBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserDiaryData
import com.hansung.traveldiary.src.diary.DiaryDayViewModel
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionActivity
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionAdapter
import com.hansung.traveldiary.src.diary.SendTravelPlanActivity
import com.hansung.traveldiary.src.diary.write_diary.WriteDiaryActivity
import com.hansung.traveldiary.src.travel.adapter.DiarySectionAdapter

class OtherUserDiarySectionAdapter (private val index: Int) :
    RecyclerView.Adapter<OtherUserDiarySectionAdapter.ViewHolder>() {

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
        val data = MainActivity.bulletinDiaryArray[index].userDiaryData.diaryArray[position]
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
            intent = Intent(context, ShowDiaryActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("day", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = MainActivity.bulletinDiaryArray[index].userDiaryData.diaryArray.size
}


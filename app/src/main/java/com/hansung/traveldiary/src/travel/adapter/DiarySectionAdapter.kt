package com.hansung.traveldiary.src.travel.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
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
import com.hansung.traveldiary.config.EditBottomDialogFragment
import com.hansung.traveldiary.databinding.ItemDiaryBinding
import com.hansung.traveldiary.src.DiaryBulletinData
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserDiaryData
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionActivity
import com.hansung.traveldiary.src.diary.SendTravelPlanActivity
import com.hansung.traveldiary.src.plan.ScheduleFragment

class DiarySectionAdapter(val userDiaryArray : ArrayList<UserDiaryData>):RecyclerView.Adapter<DiarySectionAdapter.ViewHolder>() {
    private var db: FirebaseFirestore? = null
    private var user: FirebaseUser? = null

    class ViewHolder(val binding: ItemDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.btItemTvTitle
        val thumbnail = binding.btItemIvThumbnail
        val hashtag = binding.btItemTvTag
        val likeCnt = binding.btItemTvLikecnt
        val commentCnt = binding.btItemTvComment
        val edtBtn = binding.btItemIvEdit
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemDiaryBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        user = Firebase.auth.currentUser
        db = Firebase.firestore

        val data = userDiaryArray[position]
        val context = holder.itemView.context
        holder.title.text = data.baseData.title
//        holder.hashtag.text = data.tv_tag
        val imagePath = data.baseData.mainImage
        if(imagePath != "")
            Glide.with(holder.itemView.context).load(imagePath).into(holder.thumbnail)
        else{
            Glide.with(holder.itemView.context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_no_main_image, null)).into(holder.thumbnail)
        }

        holder.likeCnt.text = data.baseData.like.toString()
        holder.commentCnt.text = data.baseData.comments.toString()
        holder.itemView.setTag(position)
        holder.edtBtn.setOnClickListener {
            val editBtmSheetDialogFragment = DeleteBottomDialogFragment {
                when (it) {
                    0 -> {
                        println("수정")
                        val intent = Intent(context, SendTravelPlanActivity::class.java)
                        intent.putExtra("index", position)
                        context.startActivity(intent)
                    }
                    1 -> {
                        println("삭제")
                        println("$userDiaryArray[position].baseData.title")
//                        userDiaryArray.removeAt(position)
//                        db!!.collection("Diary").document(user!!.email.toString())
//                            .set(userDiaryArray)

                    }
                }
            }
            editBtmSheetDialogFragment.show(
                (context as MainActivity).supportFragmentManager,
                editBtmSheetDialogFragment.tag
            )
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(context, MyDiaryDaySectionActivity::class.java)
            intent.putExtra("index", position)
            context.startActivity(intent)
            (context as MainActivity).overridePendingTransition(0, 0)
        }


    }

    override fun getItemCount(): Int = MainActivity.userDiaryArray.size

}
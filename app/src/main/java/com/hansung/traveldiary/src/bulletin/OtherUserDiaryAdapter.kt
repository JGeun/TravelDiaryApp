package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.config.DeleteBottomDialogFragment
import com.hansung.traveldiary.databinding.ItemDiaryBinding
import com.hansung.traveldiary.databinding.ItemOtherUserDiaryBinding
import com.hansung.traveldiary.src.*
import com.hansung.traveldiary.src.diary.CommentListActivity
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionActivity
import com.hansung.traveldiary.src.diary.SendTravelPlanActivity
import com.hansung.traveldiary.src.travel.adapter.DiarySectionAdapter

class OtherUserDiaryAdapter (val userDiaryArray: ArrayList<DiaryBaseData> = ArrayList(),val  index:Int):
    RecyclerView.Adapter<OtherUserDiaryAdapter.ViewHolder>() {
    private var db: FirebaseFirestore? = null
    private var user: FirebaseUser? = null

    class ViewHolder(val binding: ItemOtherUserDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.btItemTvTitle
        val thumbnail = binding.btItemIvThumbnail
        val date = binding.btItemTvDate
        val area = binding.btItemTvArea
        val person = binding.btItemTvPerson
        val ivComment=binding.btItemIvComment
        val likeCnt = binding.btItemTvLikecnt
        val commentCnt = binding.btItemTvComment
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemOtherUserDiaryBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        user = Firebase.auth.currentUser
        db = Firebase.firestore


        val context = holder.itemView.context
        val data = MainActivity.bulletinDiaryArray[position].userDiaryData
        holder.title.text = userDiaryArray[position].title
        val area = "#${userDiaryArray[position].area}"
        holder.area.text = area
        val person = "#${userDiaryArray[position].peopleCount}명이서 여행"
        holder.person.text = person
        val startDate = userDiaryArray[position].startDate.split("-")
        val endDate = userDiaryArray[position].endDate.split("-")
        val date = "${startDate[1]}/${startDate[2]} ~ ${endDate[1]}/${endDate[2]}"
        holder.date.text = date

        val imagePath = userDiaryArray[position].mainImage
        if(imagePath != "")
            Glide.with(holder.itemView.context).load(imagePath).into(holder.thumbnail)
        else{
            Glide.with(holder.itemView.context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_no_main_image, null)).into(holder.thumbnail)
        }
        Log.d("포지션", userDiaryArray[position].like.likeUserFolder.size.toString())
        println("33333333333333333333333333333333333333333333333333333")
        println(userDiaryArray[position].like.likeUserFolder.size.toString())
        holder.likeCnt.text = userDiaryArray[position].like.likeUserFolder.size.toString()
        holder.commentCnt.text = userDiaryArray[position].comments.commentsFolder.size.toString()
        holder.itemView.setTag(position)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, OtherDiarySectionActivity::class.java)
            intent.putExtra("index", 0)// 수정해야함
            intent.putExtra("email",userDiaryArray[position].userEmail)
            context.startActivity(intent)
            //(context as MainActivity).overridePendingTransition(0, 0)
        }
        /*holder.ivComment.setOnClickListener {
            val intent= Intent(context, CommentListActivity::class.java)
            intent.putExtra("index",position)
            context.startActivity(intent)
        }*/
    }

    override fun getItemCount(): Int = userDiaryArray.size

}
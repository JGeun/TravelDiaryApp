package com.hansung.traveldiary.src.travel.adapter

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
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
import com.hansung.traveldiary.src.*
import com.hansung.traveldiary.src.diary.CommentListActivity
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionActivity
import com.hansung.traveldiary.src.diary.SendTravelPlanActivity

class DiarySectionAdapter(val userDiaryArray : ArrayList<UserDiaryData>):RecyclerView.Adapter<DiarySectionAdapter.ViewHolder>() {
    private var db: FirebaseFirestore? = null
    private var user: FirebaseUser? = null

    class ViewHolder(val binding: ItemDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.btItemTvTitle
        val thumbnail = binding.btItemIvThumbnail
        val date = binding.btItemTvDate
        val area = binding.btItemTvArea
//        val person = binding.btItemTvPerson
        val ivcomment=binding.btItemIvComment
        val ivLike = binding.btItemIvLike
        val likeCnt = binding.btItemTvLikecnt
        val commentCnt = binding.btItemTvComment
        val edtBtn = binding.btItemIvEdit
        val lock = binding.lock
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
        val area = "#${data.baseData.area}"
        holder.area.text = area
//        val person = "#${data.baseData.peopleCount}명이서 여행"
//        holder.person.text = person
        val startDate = data.baseData.startDate.split("-")
        val endDate = data.baseData.endDate.split("-")
        val date = "${startDate[1]}/${startDate[2]} ~ ${endDate[1]}/${endDate[2]}"
        holder.date.text = date

        val imagePath = data.baseData.mainImage
        if(imagePath != "")
            Glide.with(holder.itemView.context).load(imagePath).into(holder.thumbnail)
        else{
            Glide.with(holder.itemView.context).load(ResourcesCompat.getDrawable(context.resources, R.drawable.img_no_main_image, null)).into(holder.thumbnail)
        }
        
        holder.likeCnt.text = data.baseData.like.likeUserFolder.size.toString()
        holder.commentCnt.text = data.baseData.comments.commentsFolder.size.toString()
        holder.itemView.setTag(position)
        holder.edtBtn.setOnClickListener {
            val editBtmSheetDialogFragment = DeleteBottomDialogFragment {
                when (it) {
                    0 -> {
                        println("수정")
                        val intent = Intent(context, SendTravelPlanActivity::class.java)
                        intent.putExtra("index", position)
                        intent.putExtra("isModify", true)
                        context.startActivity(intent)
                    }
                    1 -> {
                        (context as MainActivity).removeDiary(position)
                        notifyDataSetChanged()
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

        holder.commentCnt.setOnClickListener {
            val intent =Intent(context, CommentListActivity::class.java)
            intent.putExtra("myDiary",true)
            intent.putExtra("index",position)
            context.startActivity(intent)

        }
        holder.ivcomment.setOnClickListener {
            val intent =Intent(context, CommentListActivity::class.java)
            intent.putExtra("myDiary",true)
            intent.putExtra("index",position)
            context.startActivity(intent)

        }
        val diaryIdxRef = db!!.collection("Diary").document(user!!.email.toString())
        var lock= data.baseData.lock
        val idx=data.baseData.idx
        if(lock == "false"){
            Glide.with(holder.itemView.context).load(R.drawable.ic_unlock).into(holder.lock)
        }else if(lock == "true"){
            Glide.with(holder.itemView.context).load(R.drawable.lock_gray).into(holder.lock)
        }

        holder.lock.setOnClickListener {
            if(lock == "false"){
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_lock, null)
                val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogView)
                val mAlertDialog=mBuilder.show()
                mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val okbtn=mDialogView.findViewById<Button>(R.id.btn_yes)
                okbtn.setOnClickListener {
                    Glide.with(holder.itemView.context).load(R.drawable.lock_gray).into(holder.lock)
                    lock="true"
                    val diaryBaseData = DiaryBaseData(
                        data.baseData.idx,
                        data.baseData.title,
                        data.baseData.mainImage,
                        user!!.email.toString(),
                        data.baseData.uploadDate,
                        data.baseData.startDate,
                        data.baseData.endDate,
                        data.baseData.color,
                        data.baseData.area,
                        data.baseData.friendsList,
                        LikeFolder(),
                        CommentsFolder(),
                        lock
                    )
                    diaryIdxRef.collection("DiaryData").document(idx.toString()).set(diaryBaseData)
                    //여기 넣어야함.
                    val bulletinIdxRef =  db!!.collection("Bulletin").document("BulletinData")
                    MainActivity.bulletinIdxList.idxFolder.remove(data.baseData.idx)
                    bulletinIdxRef.set(MainActivity.bulletinIdxList)

                    MainActivity.userDiaryArray[position].baseData.lock = "true"
                    for(i in 0 until MainActivity.bulletinDiaryArray.size){
                        if(MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.idx == data.baseData.idx){
                            MainActivity.bulletinDiaryArray.removeAt(i)
                            break
                        }
                    }

                    mAlertDialog.dismiss()
                }
                val cancelbtn=mDialogView.findViewById<Button>(R.id.btn_no)
                cancelbtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }

            }else if(lock == "true"){
                val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_lock, null)
                val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogView)
                val alertText=mDialogView.findViewById<TextView>(R.id.alert_text)
                alertText.text="다이어리를 공개 하시겠어요?"
                val mAlertDialog=mBuilder.show()
                mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val okbtn=mDialogView.findViewById<Button>(R.id.btn_yes)
                okbtn.setOnClickListener {
                    Glide.with(holder.itemView.context).load(R.drawable.ic_unlock).into(holder.lock)
                    lock="false"
                    val diaryBaseData = DiaryBaseData(
                        data.baseData.idx,
                        data.baseData.title,
                        data.baseData.mainImage,
                        user!!.email.toString(),
                        data.baseData.uploadDate,
                        data.baseData.startDate,
                        data.baseData.endDate,
                        data.baseData.color,
                        data.baseData.area,
                        data.baseData.friendsList,
                        LikeFolder(),
                        CommentsFolder(),
                        lock
                    )
                    diaryIdxRef.collection("DiaryData").document(idx.toString()).set(diaryBaseData)
                    val bulletinIdxRef =  db!!.collection("Bulletin").document("BulletinData")
                    MainActivity.bulletinIdxList.idxFolder.add(data.baseData.idx)
                    bulletinIdxRef.set(MainActivity.bulletinIdxList)
                    MainActivity.userDiaryArray[position].baseData.lock = "false"

                    db!!.collection("UserInfo").document(user!!.email.toString())
                        .get().addOnSuccessListener { result ->
                            val userInfo = result.toObject<UserInfo>()!!
                            bulletinIdxRef.collection(data.baseData.idx.toString()).document("idxUserData").set(userInfo)
                            MainActivity.bulletinDiaryArray.add(BulletinData(MainActivity.userDiaryArray[position], userInfo))
                        }

                    mAlertDialog.dismiss()
                }
                val cancelbtn=mDialogView.findViewById<Button>(R.id.btn_no)
                cancelbtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
        }
    }

    override fun getItemCount(): Int = MainActivity.userDiaryArray.size

}

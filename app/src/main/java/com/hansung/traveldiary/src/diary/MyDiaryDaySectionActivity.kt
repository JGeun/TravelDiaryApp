package com.hansung.traveldiary.src.diary

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMyDiaryDaySectionBinding
import com.hansung.traveldiary.src.*
import com.hansung.traveldiary.util.StatusBarUtil

class MyDiaryDaySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMyDiaryDaySectionBinding.inflate(layoutInflater)
    }
    private lateinit var likeCount:LikeViewModel
    private val viewModel :DiaryDayViewModel by viewModels()
    private var index = 0

    private var user : FirebaseUser? = null
    private var chk_like=false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)
        likeCount=LikeViewModel()
        user = Firebase.auth.currentUser
        val db = Firebase.firestore

        val showDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_find_black, null)
        val writeDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_edit, null)



        binding.dsIvBack.setOnClickListener{
            finish()
            overridePendingTransition(0, 0)
        }

        index = intent.getIntExtra("index", 0)
        var lock=MainActivity.userDiaryArray[index].baseData.lock.toString()
        val diaryIdxRef = db!!.collection("Diary").document(user!!.email.toString())
        val idx= MainActivity.userDiaryArray[index].baseData.idx
        if(lock == "false"){
            Glide.with(this).load(R.drawable.ic_unlock).into(binding.ivLock)
        }else if(lock == "true") {
            Glide.with(this).load(R.drawable.lock_gray).into(binding.ivLock)
        }

        binding.ivLock.setOnClickListener {
            if (lock == "false") {
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_lock, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                val mAlertDialog = mBuilder.show()
                mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val okbtn = mDialogView.findViewById<Button>(R.id.btn_yes)
                okbtn.setOnClickListener {
                    Glide.with(this).load(R.drawable.lock_gray).into(binding.ivLock)
                    lock = "true"
                    val diaryBaseData = DiaryBaseData(
                        MainActivity.userDiaryArray[index].baseData.idx,
                        MainActivity.userDiaryArray[index].baseData.title,
                        MainActivity.userDiaryArray[index].baseData.mainImage,
                        user!!.email.toString(),
                        MainActivity.userDiaryArray[index].baseData.uploadDate,
                        MainActivity.userDiaryArray[index].baseData.startDate,
                        MainActivity.userDiaryArray[index].baseData.endDate,
                        MainActivity.userDiaryArray[index].baseData.color,
                        MainActivity.userDiaryArray[index].baseData.area,
                        MainActivity.userDiaryArray[index].baseData.friendsList,
                        LikeFolder(),
                        CommentsFolder(),
                        lock
                    )
                    diaryIdxRef.collection("DiaryData").document(idx.toString()).set(diaryBaseData)
                    val bulletinIdxRef =  db!!.collection("Bulletin").document("BulletinData")
                    MainActivity.bulletinIdxList.idxFolder.remove(MainActivity.userDiaryArray[index].baseData.idx)
                    bulletinIdxRef.set(MainActivity.bulletinIdxList)

                    MainActivity.userDiaryArray[index].baseData.lock = "true"
                    for(i in 0 until MainActivity.bulletinDiaryArray.size){
                        if(MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.idx == MainActivity.userDiaryArray[index].baseData.idx){
                            MainActivity.bulletinDiaryArray.removeAt(i)
                            break
                        }
                    }
                    mAlertDialog.dismiss()
                }
                val cancelbtn = mDialogView.findViewById<Button>(R.id.btn_no)
                cancelbtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }

            } else if (lock == "true") {
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_lock, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                val alertText = mDialogView.findViewById<TextView>(R.id.alert_text)
                alertText.text = "다이어리를 공개 하시겠어요?"
                val mAlertDialog = mBuilder.show()
                mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val okbtn = mDialogView.findViewById<Button>(R.id.btn_yes)

                okbtn.setOnClickListener {
                    for (i in 0 until MainActivity.userDiaryArray[index].diaryArray.size){
                        if (MainActivity.userDiaryArray[index].diaryArray[i].diaryInfo.imagePathArray.size==0||
                            MainActivity.userDiaryArray[index].diaryArray[i].diaryInfo.diaryTitle==""||
                            MainActivity.userDiaryArray[index].diaryArray[i].diaryInfo.diaryContents==""){
                            val mCheckDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_lock_check, null)
                            val mCheckBuilder = AlertDialog.Builder(this).setView(mCheckDialogView)
                            val mAlertCheckDialog = mCheckBuilder.show()
                            mAlertCheckDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            mCheckDialogView.findViewById<Button>(R.id.btn_ok).setOnClickListener {
                                mAlertCheckDialog.dismiss()
                            }
                            break
                        }else{
                            Glide.with(this).load(R.drawable.ic_unlock).into(binding.ivLock)
                            lock = "false"
                            val diaryBaseData = DiaryBaseData(
                                MainActivity.userDiaryArray[index].baseData.idx,
                                MainActivity.userDiaryArray[index].baseData.title,
                                MainActivity.userDiaryArray[index].baseData.mainImage,
                                user!!.email.toString(),
                                MainActivity.userDiaryArray[index].baseData.uploadDate,
                                MainActivity.userDiaryArray[index].baseData.startDate,
                                MainActivity.userDiaryArray[index].baseData.endDate,
                                MainActivity.userDiaryArray[index].baseData.color,
                                MainActivity.userDiaryArray[index].baseData.area,
                                MainActivity.userDiaryArray[index].baseData.friendsList,
                                LikeFolder(),
                                CommentsFolder(),
                                lock
                            )
                            diaryIdxRef.collection("DiaryData").document(idx.toString()).set(diaryBaseData)
                            val bulletinIdxRef =  db!!.collection("Bulletin").document("BulletinData")
                            MainActivity.bulletinIdxList.idxFolder.remove(MainActivity.userDiaryArray[index].baseData.idx)
                            bulletinIdxRef.set(MainActivity.bulletinIdxList)

                            MainActivity.userDiaryArray[index].baseData.lock = "true"
                            for(i in 0 until MainActivity.bulletinDiaryArray.size){
                                if(MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.idx == MainActivity.userDiaryArray[index].baseData.idx){
                                    MainActivity.bulletinDiaryArray.removeAt(i)
                                    break
                                }
                            }
                            mAlertDialog.dismiss()
                        }
                    }
                    mAlertDialog.dismiss()
                }
                val cancelbtn = mDialogView.findViewById<Button>(R.id.btn_no)
                cancelbtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
        }
        binding.dsTitle.text = MainActivity.userDiaryArray[index].baseData.title

        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= MyDiaryDaySectionAdapter(index, viewModel)
            layoutManager= LinearLayoutManager(this@MyDiaryDaySectionActivity)
        }

        val likeRef = db.collection("Diary").document(user!!.email.toString())
            .collection("DiaryData").document(MainActivity.userDiaryArray[index].baseData.idx.toString())

        if(!MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.contains(user!!.email.toString())){
            binding.ivLike.setImageResource(R.drawable.emptyheart)
            likeRef.set(MainActivity.userDiaryArray[index].baseData)
            binding.countLikes.text = MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.size.toString()
        }else{
            binding.ivLike.setImageResource(R.drawable.asset7)
            likeRef.set(MainActivity.userDiaryArray[index].baseData)
            binding.countLikes.text = MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.size.toString()
        }

        binding.ivLike.setOnClickListener {
            if(!MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.contains(user!!.email.toString())){
                binding.ivLike.setImageResource(R.drawable.asset7)
                MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.add(user!!.email.toString())
                likeRef.set(MainActivity.userDiaryArray[index].baseData)
                binding.countLikes.text = MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.size.toString()
            }else{
                binding.ivLike.setImageResource(R.drawable.emptyheart)
                MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.remove(user!!.email.toString())
                likeRef.set(MainActivity.userDiaryArray[index].baseData)
                binding.countLikes.text = MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.size.toString()
            }
        }

        binding.commentLayout.setOnClickListener {
            val intent=Intent(this, CommentListActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("myDiary", true)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("체크", "MyDiaryDaySection Start")

        if(MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.contains(user!!.email.toString())) {
            chk_like = true
            binding.ivLike.setImageResource(R.drawable.asset7)
        }else{
            chk_like = false
            binding.ivLike.setImageResource(R.drawable.emptyheart)
        }

        binding.countComments.text = MainActivity.userDiaryArray[index].baseData.comments.commentsFolder.size.toString()
        binding.countLikes.text = MainActivity.userDiaryArray[index].baseData.like.likeUserFolder.size.toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0, 0)
    }
}
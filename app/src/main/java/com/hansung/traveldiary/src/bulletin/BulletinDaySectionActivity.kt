package com.hansung.traveldiary.src.bulletin

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityBulletinDaySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.util.StatusBarUtil
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.src.diary.CommentListActivity

class BulletinDaySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityBulletinDaySectionBinding.inflate(layoutInflater)
    }
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    private var index = 0
    private var chk_like=false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)
        user = FirebaseAuth.getInstance().currentUser
        db = Firebase.firestore

        println("BulletinDaySectionActivity 들어옴")
        index = intent.getIntExtra("index", 0)
        println("받은 index: ${index}")
        //프로필 이미지 설정

        val userImagePath = MainActivity.bulletinDiaryArray[index].userInfo.profileImage
        if(userImagePath == "")
            Glide.with(this).load(ResourcesCompat.getDrawable(this.resources, R.drawable.img_beach, null)).circleCrop().into(binding.ivProfileImage)
        else
            Glide.with(this).load(userImagePath).circleCrop().into(binding.ivProfileImage)


        binding.bdsTitle.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.title
        binding.bdsIvBack.setOnClickListener{
            finish()
        }
        binding.bdsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= BulletinDaySectionAdapter(index, MainActivity.bulletinDiaryArray[index].userDiaryData.diaryArray.size,userImagePath)
            layoutManager=LinearLayoutManager(this@BulletinDaySectionActivity)
        }

        binding.ivProfileImage.setOnClickListener {
            val intent=Intent(this,OtherUserActivity::class.java)
            intent.putExtra("index", index)
            startActivity(intent)
        }


        val likeRef = db!!.collection("Diary").document(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.userEmail)
            .collection("DiaryData").document(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.idx.toString())
        binding.ivLike.setOnClickListener {
            if(!chk_like){
                binding.ivLike.setImageResource(R.drawable.asset7)
                chk_like=true
                MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.add(user!!.email.toString())
                likeRef.set(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData)
                binding.countLikes.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
            }else{
                binding.ivLike.setImageResource(R.drawable.emptyheart)
                chk_like=false
                MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.remove(user!!.email.toString())
                likeRef.set(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData)
                binding.countLikes.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
            }
        }

        binding.commentLayout.setOnClickListener {
            val intent=Intent(this, CommentListActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("myDiary", false)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d("체크", "BulletinDaySection Start")
        if(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.contains(user!!.email.toString())) {
            chk_like = true
            binding.ivLike.setImageResource(R.drawable.asset7)
        }else{
            chk_like = false
            binding.ivLike.setImageResource(R.drawable.emptyheart)
        }

        binding.countComments.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.comments.commentsFolder.size.toString()
        binding.countLikes.text =  MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
    }

    override fun onPause() {
        super.onPause()
        Log.d("체크", "BulletinDaySection Pause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("체크", "BulletinDaySection Destroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("체크", "BulletinDaySection ReStart")
    }
}
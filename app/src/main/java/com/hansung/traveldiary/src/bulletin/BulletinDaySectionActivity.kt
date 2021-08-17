package com.hansung.traveldiary.src.bulletin

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class BulletinDaySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityBulletinDaySectionBinding.inflate(layoutInflater)
    }
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)
        user = FirebaseAuth.getInstance().currentUser
        db = Firebase.firestore
        println("BulletinDaySectionActivity 들어옴")
        val index = intent.getIntExtra("index", 0)
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
    }
}
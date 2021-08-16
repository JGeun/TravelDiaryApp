package com.hansung.traveldiary.src.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityOtherUserBinding
import com.hansung.traveldiary.src.MainActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.src.travel.adapter.DiarySectionAdapter

class OtherUserActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityOtherUserBinding.inflate(layoutInflater)
    }
    private val diarySectionAdapter = OtherUserDiaryAdapter(MainActivity.userDiaryArray)
    private var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
        val userName=intent.getStringExtra("nickname")
        val userImagePath=intent.getStringExtra("image")
        val email=intent.getStringExtra("email")
        val idx=MainActivity.userList.emailFolder.indexOf(email)
        //println(db!!.collection("DiaryData").document(email.toString()).collection("DiaryDaya"))
        println("게시글을 작성한 유저의 이메일"+email.toString())
        println("게시글을 작성한 유저의 이메일 인덱스는 "+idx)
        binding.userName.text=userName.toString()
        //프로필 이미지 유무 검사
        if(userImagePath == "")
            Glide.with(this).load(ResourcesCompat.getDrawable(this.resources, R.drawable.img_basic_profile, null)).circleCrop().into(binding.userProfileImage)
        else
        //Glide.with(this).load(userImagePath).circleCrop().into(binding.ivProfileImage)
            Glide.with(this).load(ResourcesCompat.getDrawable(this.resources, R.drawable.img_basic_profile, null)).circleCrop().into(binding.userProfileImage)

        //println(MainActivity.userPlanArray[0].baseData)
        binding.planCount.text = MainActivity.userPlanArray.size.toString()
        binding.diaryCount.text = MainActivity.userDiaryArray.size.toString()
        binding.rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = diarySectionAdapter
        }
        //기능구현 x
        binding.btnAddFriend.setOnClickListener {
            Toast.makeText(this,"친구 추가가 완료되었습니다", Toast.LENGTH_SHORT).show()
        }
        setContentView(binding.root)
    }
}
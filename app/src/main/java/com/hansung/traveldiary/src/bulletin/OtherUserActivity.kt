package com.hansung.traveldiary.src.bulletin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityOtherUserBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.src.*
import com.hansung.traveldiary.src.travel.adapter.DiarySectionAdapter

class OtherUserActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityOtherUserBinding.inflate(layoutInflater)
    }

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        window.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN }

        user = Firebase.auth.currentUser
        db = Firebase.firestore
        val index = intent.getIntExtra("index", 0)
        val email = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.userEmail
        val nickname = MainActivity.bulletinDiaryArray[index].userInfo.nickname
        val userImagePath = MainActivity.bulletinDiaryArray[index].userInfo.profileImage
        binding.userName.text = nickname
        //println(db!!.collection("DiaryData").document(email.toString()).collection("DiaryDaya"))
        //프로필 이미지 유무 검사

        if(userImagePath == "")
            Glide.with(this).load(ResourcesCompat.getDrawable(this.resources, R.drawable.img_basic_profile, null)).circleCrop().into(binding.userProfileImage)
        else
            Glide.with(this).load(userImagePath).circleCrop().into(binding.userProfileImage)

        if(user!!.email.toString().equals(email.toString())){
            binding.btnLayout.isGone=true
        }


        Log.d("프로필", "email: ${MainActivity.bulletinDiaryArray[index].userInfo.email}")
        println("현재 로그인 중인 유저의 이메일은 : "+ user!!.email.toString())

        val userDiaryArray = ArrayList<UserDiaryData>()
        for(i in 0 until MainActivity.bulletinDiaryArray.size){
            if(MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.userEmail == email){
                userDiaryArray.add(MainActivity.bulletinDiaryArray[i].userDiaryData)
            }
        }
        binding.rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = OtherUserDiaryAdapter(userDiaryArray,index)
        }


        binding.btnAddFriend.setOnClickListener {
            val userRef = db!!.collection("UserInfo").document(user!!.email.toString())
            userRef.get()
                .addOnSuccessListener { result ->
                    val data = result.toObject<UserInfo>()
                    if(data != null){
                        val userInfo = data
                        userInfo.friendList.friendFolder.add(email)
                        userRef.set(userInfo).addOnSuccessListener {
                            Toast.makeText(this,"친구 추가가 완료되었습니다", Toast.LENGTH_SHORT).show()
                            MainActivity.myFriendList.friendFolder.add(email)
                        }.addOnFailureListener{
                            Toast.makeText(this,"친구 추가에 실패했습니다", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this,"친구 추가에 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }
}
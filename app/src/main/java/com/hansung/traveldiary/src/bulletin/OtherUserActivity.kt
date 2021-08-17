package com.hansung.traveldiary.src.bulletin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.hansung.traveldiary.src.IdxList
import com.hansung.traveldiary.src.UserDiaryData
import com.hansung.traveldiary.src.travel.adapter.DiarySectionAdapter

class OtherUserActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityOtherUserBinding.inflate(layoutInflater)
    }

    private var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        window.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN }

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

        binding.planCount.text = "0"
        binding.friendsCount.text = MainActivity.bulletinDiaryArray[index].userInfo.friendList.size.toString()



        Log.d("프로필", "email: ${email}")
        val userDiaryArray = ArrayList<UserDiaryData>()
        for(i in 0 until MainActivity.bulletinDiaryArray.size){
            if(MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.userEmail == email){
                userDiaryArray.add(MainActivity.bulletinDiaryArray[i].userDiaryData)
            }
        }
        binding.diaryCount.text = userDiaryArray.size.toString()
        binding.rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = OtherUserDiaryAdapter(userDiaryArray)
        }

//        val idxRef = db!!.collection("Diary").document(email)
//        idxRef.get()
//            .addOnSuccessListener { result ->
//                val data = result.toObject<IdxList>()
//                if(data != null){
//                    binding.diaryCount.text = data.idxFolder.size.toString()
//                    val userDiaryArray = ArrayList<UserDiaryData>()
//                    val dataRef  = idxRef.collection("DiaryData")
//                    for(idx in data.idxFolder){
//                        dataRef.document(idx.toString()).get()
//                    }
//
//                }else{
//                    binding.diaryCount.text = "0"
//                }
//        }




        //기능구현 x
        binding.btnAddFriend.setOnClickListener {
            Toast.makeText(this,"친구 추가가 완료되었습니다", Toast.LENGTH_SHORT).show()
        }
        setContentView(binding.root)
    }
}
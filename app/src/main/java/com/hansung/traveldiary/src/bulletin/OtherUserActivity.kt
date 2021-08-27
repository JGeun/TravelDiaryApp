package com.hansung.traveldiary.src.bulletin

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import kotlinx.coroutines.*

class OtherUserActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityOtherUserBinding.inflate(layoutInflater)
    }
    companion object{

    }
    private var countlike=Integer.MAX_VALUE
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
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        user = Firebase.auth.currentUser
        db = Firebase.firestore
        val index = intent.getIntExtra("index", 0)
        val email = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.userEmail
        val nickname = MainActivity.bulletinDiaryArray[index].userInfo.nickname
        val userImagePath = MainActivity.bulletinDiaryArray[index].userInfo.profileImage
        binding.userName.text = nickname
        //println(db!!.collection("DiaryData").document(email.toString()).collection("DiaryDaya"))
        //프로필 이미지 유무 검사

        if (userImagePath == "")
            Glide.with(this).load(
                ResourcesCompat.getDrawable(
                    this.resources,
                    R.drawable.img_basic_profile,
                    null
                )
            ).circleCrop().into(binding.userProfileImage)
        else
            Glide.with(this).load(userImagePath).circleCrop().into(binding.userProfileImage)

        if (user!!.email.toString().equals(email.toString())) {
            binding.btnLayout.isGone = true
        }


        Log.d("프로필", "email: ${MainActivity.bulletinDiaryArray[index].userInfo.email}")
        println("현재 로그인 중인 유저의 이메일은 : " + user!!.email.toString())
        println(email)
        val userDiaryArray = ArrayList<UserDiaryData>()
        for (i in 0 until MainActivity.bulletinDiaryArray.size) {
            if (MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.userEmail == email) {
                userDiaryArray.add(MainActivity.bulletinDiaryArray[i].userDiaryData)
            }
        }

        getDiary(email,index)




        binding.btnAddFriend.setOnClickListener {
            val userRef = db!!.collection("UserInfo").document(user!!.email.toString())
            userRef.get()
                .addOnSuccessListener { result ->
                    val data = result.toObject<UserInfo>()
                    if (data != null) {
                        val userInfo = data
                        userInfo.friendList.friendFolder.add(email)
                        userRef.set(userInfo).addOnSuccessListener {
                            Toast.makeText(this, "친구 추가가 완료되었습니다", Toast.LENGTH_SHORT).show()
                            MainActivity.myFriendList.friendFolder.add(email)
                        }.addOnFailureListener {
                            Toast.makeText(this, "친구 추가에 실패했습니다", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "친구 추가에 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

     fun getDiary(email: String,index:Int) {
         val DBD = ArrayList<DiaryBaseData>()
         val idxList = IdxList()
         var diaryIdxList = IdxList()
         val diaryIdxRef =
             db!!.collection("Diary").document(email.toString())
         diaryIdxRef.get()
             .addOnSuccessListener { result ->
                 val idxData = result.toObject<IdxList>()
                 println(idxData.toString())
                 if (idxData != null) {
                     diaryIdxList = idxData
                     println("폴더의 길이" + diaryIdxList.idxFolder.size.toString())
                     for (idx in diaryIdxList.idxFolder) {
                         var diaryBaseData = DiaryBaseData()
                         println("${email} idx: ${idx}")
                         val baseRef =
                             diaryIdxRef.collection("DiaryData").document(idx.toString())
                         baseRef.get().addOnSuccessListener { baseResult ->
                             val baseData = baseResult.toObject<DiaryBaseData>()
                             if (baseData != null) {
                                 diaryBaseData = baseData
                                 DBD.add(diaryBaseData)
                             }
                             println("길이" + DBD.size)
                             binding.rv.apply {
                                 setHasFixedSize(true)
                                 layoutManager = LinearLayoutManager(context)
                                 adapter = OtherUserDiaryAdapter(DBD,index)
                             }
                         }

                     }

                 }
             }
     }

}
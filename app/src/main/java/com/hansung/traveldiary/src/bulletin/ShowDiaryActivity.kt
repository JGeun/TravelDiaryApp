package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityShowDiaryBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserDiaryData
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.src.diary.CommentListActivity

class ShowDiaryActivity : AppCompatActivity(){
    private val binding by lazy{
        ActivityShowDiaryBinding.inflate(layoutInflater)
    }
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance().currentUser
        db = Firebase.firestore
        println("현재 유저의 이메일은 "+ user?.email.toString())

        window?.apply {
            this.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        //        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.transparent_STATUS_BAR)

        val index = intent.getIntExtra("index", 0)
        val day = intent.getIntExtra("day", 0)
        val isBulletin = intent.getBooleanExtra("isBulletin", false)
        val profileImage=intent.getStringExtra("image")
        val email=intent.getStringExtra("email")
        println(profileImage.toString())
        println(email.toString())
        val diary : UserDiaryData

        if(isBulletin){
            diary= MainActivity.bulletinDiaryArray[index].userDiaryData
        }else{
            diary = MainActivity.userDiaryArray[index]
        }

        var chk_like=false
        if(diary.baseData.like.likeUserFolder.contains(user!!.email.toString())) {
            chk_like = true
            binding.ivLike.setImageResource(R.drawable.asset7)
        }

        binding.countComments.text = diary.baseData.comments.commentsFolder.size.toString()
        binding.countLikes.text = diary.baseData.like.likeUserFolder.size.toString()

        val likeRef = db!!.collection("Diary").document(diary.baseData.userEmail)
            .collection("DiaryData").document(diary.baseData.idx.toString())
        binding.ivLike.setOnClickListener {
            if(!chk_like){
                binding.ivLike.setImageResource(R.drawable.asset7)
                chk_like=true
                diary.baseData.like.likeUserFolder.add(user!!.email.toString())
                likeRef.set(diary.baseData)
                binding.countLikes.text = diary.baseData.like.likeUserFolder.size.toString()
            }else{
                binding.ivLike.setImageResource(R.drawable.emptyheart)
                chk_like=false
                diary.baseData.like.likeUserFolder.remove(user!!.email.toString())
                likeRef.set(diary.baseData)
                binding.countLikes.text = diary.baseData.like.likeUserFolder.size.toString()
            }
        }

        binding.commentLayout.setOnClickListener {
            val intent=Intent(this, CommentListActivity::class.java)
            intent.putExtra("index",index)
            if(isBulletin)
                intent.putExtra("myDiary", false)
            else
                intent.putExtra("myDiary", true)
            startActivity(intent)
        }

        println("Show index: ${index} day: ${day}")
        binding.sdDate.text = afterDate(diary.baseData.startDate, day)
        binding.sdTitle.text = diary.diaryArray[day].diaryInfo.diaryTitle
        binding.sdContents.text = diary.diaryArray[day].diaryInfo.diaryContents
        println("Show Title: ${diary.diaryArray[day].diaryInfo.diaryTitle}")
        println("Show Contents: ${diary.diaryArray[day].diaryInfo.diaryContents}")
        val default_imgArr:ArrayList<String> = ArrayList<String>()
        default_imgArr.add("https://media.istockphoto.com/photos/suitcase-minimal-travel-concept-with-blue-background-picture-id1130628787?k=6&m=1130628787&s=612x612&w=0&h=Dg-E5XfMagOFGGSA0kHGSZfkgVw_uE9i1ieGk5SDX_8=")
        if(diary.diaryArray[day].diaryInfo.imagePathArray.size!=0) {
            print("이미지가 있어요")
            binding.sdViewPager.adapter =
                ShowDiaryVPAdapter(diary.diaryArray[day].diaryInfo.imagePathArray,false)
        }else{
            println("이미지가 없어요")
            binding.sdViewPager.adapter =
                ShowDiaryVPAdapter(default_imgArr,true)
        }
        binding.sdViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.sdIndicator.setViewPager(binding.sdViewPager)
        binding.sdIvBack.setOnClickListener {
            finish()
        }


        binding.ivProfileImage.setOnClickListener {
            val intent= Intent(this,OtherUserActivity::class.java)
            intent.putExtra("nickname",MainActivity.userInfoList[index].nickname.toString())
            intent.putExtra("image",profileImage)
            intent.putExtra("email",MainActivity.userList.emailFolder[index].toString())
            startActivity(intent)
        }
        //유저 프로필 이미
        if(profileImage==""){
            Glide.with(this).load(ResourcesCompat.getDrawable(this.resources, R.drawable.img_beach, null)).circleCrop().into(binding.ivProfileImage)
        }else{
            //Glide.with(this).load(userImagePath).circleCrop().into(binding.ivProfileImage)
            Glide.with(this).load(ResourcesCompat.getDrawable(this.resources, R.drawable.img_beach, null)).circleCrop().into(binding.ivProfileImage)
        }
    }

    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }
}
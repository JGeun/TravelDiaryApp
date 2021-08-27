package com.hansung.traveldiary.src.diary

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMyDiaryDaySectionBinding
import com.hansung.traveldiary.src.MainActivity
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

        binding.dsChangeImage.setImageDrawable(ResourcesCompat.getDrawable(resources, viewModel.imageData.value!!, null))

        binding.dsChangeImage.setOnClickListener{
            viewModel.change()
            binding.dsChangeImage.setImageDrawable(ResourcesCompat.getDrawable(resources, viewModel.imageData.value!!, null))
        }

        binding.dsIvBack.setOnClickListener{
            finish()
            overridePendingTransition(0, 0)
        }

        index = intent.getIntExtra("index", 0)

        binding.dsTitle.text = MainActivity.userDiaryArray[index].baseData.title

        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= MyDiaryDaySectionAdapter(index, viewModel)
            layoutManager= LinearLayoutManager(this@MyDiaryDaySectionActivity)
        }




        val likeRef = db.collection("Diary").document(user!!.email.toString())
            .collection("DiaryData").document(MainActivity.userDiaryArray[index].baseData.idx.toString())

        if(!MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.contains(user!!.email.toString())){
            binding.ivLike.setImageResource(R.drawable.emptyheart)
            likeRef.set(MainActivity.userDiaryArray[index].baseData)
            binding.countLikes.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
        }else{
            binding.ivLike.setImageResource(R.drawable.asset7)
            likeRef.set(MainActivity.userDiaryArray[index].baseData)
            binding.countLikes.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
        }

        binding.ivLike.setOnClickListener {
            if(!MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.contains(user!!.email.toString())){
                binding.ivLike.setImageResource(R.drawable.asset7)
                MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.add(user!!.email.toString())
                likeRef.set(MainActivity.userDiaryArray[index].baseData)
                binding.countLikes.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
            }else{
                binding.ivLike.setImageResource(R.drawable.emptyheart)
                MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.remove(user!!.email.toString())
                likeRef.set(MainActivity.userDiaryArray[index].baseData)
                binding.countLikes.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
            }
        }

        binding.commentLayout.setOnClickListener {
            val intent=Intent(this, CommentListActivity::class.java)
            intent.putExtra("index", index)
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
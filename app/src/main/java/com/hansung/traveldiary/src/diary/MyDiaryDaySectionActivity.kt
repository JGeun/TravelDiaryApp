package com.hansung.traveldiary.src.diary

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        println("여기 들어옴")
        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)
        likeCount=LikeViewModel()
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

        val index = intent.getIntExtra("index", 0)

        binding.dsTitle.text = MainActivity.userDiaryArray[index].baseData.title

        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= MyDiaryDaySectionAdapter(index, viewModel)
            layoutManager= LinearLayoutManager(this@MyDiaryDaySectionActivity)
        }
        var chk_like=false
        var count_like=binding.countLikes.text.toString()

        binding.ivLike.setOnClickListener {
            if(!chk_like){
                binding.ivLike.setImageResource(R.drawable.ic_full_heart)
                chk_like=true
            }else{
                binding.ivLike.setImageResource(R.drawable.emptyheart)
                chk_like=false
            }
        }

        binding.commentLayout.setOnClickListener {
            val intent=Intent(this, CommentListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.dsRecyclerview.adapter!!.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0, 0)
    }
}
package com.hansung.traveldiary.src.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMyDiaryDaySectionBinding
import com.hansung.traveldiary.databinding.ActivityOtherDiarySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.diary.DiaryDayViewModel
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionAdapter
import com.hansung.traveldiary.util.StatusBarUtil

class OtherDiarySectionActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityOtherDiarySectionBinding.inflate(layoutInflater)
    }
    private val viewModel: DiaryDayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        println("여기 들어옴")
        StatusBarUtil.setStatusBarColor(
            this,
            StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR
        )

        val showDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_find_black, null)
        val writeDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_edit, null)
        binding.dsIvBack.setOnClickListener {
            finish()
            //overridePendingTransition(0, 0)
        }

        val index = intent.getIntExtra("index", 0)

        binding.dsTitle.text = MainActivity.userDiaryArray[index].baseData.title

        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter = OtherUserDiarySectionAdapter(index, viewModel)
            layoutManager = LinearLayoutManager(this@OtherDiarySectionActivity)
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
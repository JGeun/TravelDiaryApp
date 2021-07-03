package com.hansung.traveldiary.src.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityDiarySectionBinding
import com.hansung.traveldiary.util.StatusBarUtil

class DiarySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityDiarySectionBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)

        val PostryList=arrayListOf(
            DiarySectionData(R.drawable.gwangwhamun, "#부산 #해운대"),
            DiarySectionData(R.drawable.gwangwhamun,"#부산 #해운대"),
            DiarySectionData(R.drawable.gwangwhamun,"#부산 #해운대")
        )

        binding.dsIvBack.setOnClickListener{
            finish()
        }
        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= DiarySectionAdapter(PostryList)
            layoutManager=LinearLayoutManager(this@DiarySectionActivity)
        }
    }
}
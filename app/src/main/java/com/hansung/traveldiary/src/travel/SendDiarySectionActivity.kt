package com.hansung.traveldiary.src.travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityDiarySectionBinding
import com.hansung.traveldiary.databinding.ActivitySendDiarySectionBinding
import com.hansung.traveldiary.src.bulletin.DiarySectionAdapter
import com.hansung.traveldiary.src.bulletin.DiarySectionData
import com.hansung.traveldiary.src.travel.adapter.SendDiarySectionAdapter
import com.hansung.traveldiary.util.StatusBarUtil

class SendDiarySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySendDiarySectionBinding.inflate(layoutInflater)
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
            adapter= SendDiarySectionAdapter(PostryList)
            layoutManager= LinearLayoutManager(this@SendDiarySectionActivity)
        }
    }
}
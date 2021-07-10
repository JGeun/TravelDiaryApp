package com.hansung.traveldiary.src.profile.edit_info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityEditInfoBinding
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity
import com.hansung.traveldiary.util.StatusBarUtil

class EditInfoActivity : AppCompatActivity() {
    private val binding : ActivityEditInfoBinding by lazy{
        ActivityEditInfoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        Glide.with(this).load(ResourcesCompat.getDrawable(resources, R.drawable.img_bg_profile, null)).circleCrop().into(binding.editIvProfile)
        binding.editIvProfile.setOnClickListener{
            startActivity(Intent(this, SelectPictureActivity::class.java))
        }


    }
}
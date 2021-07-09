package com.hansung.traveldiary.src.profile.edit_info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityEditInfoBinding
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity

class EditInfoActivity : AppCompatActivity() {
    private val binding : ActivityEditInfoBinding by lazy{
        ActivityEditInfoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editIvProfile.setOnClickListener{
            startActivity(Intent(this, SelectPictureActivity::class.java))
        }


    }
}
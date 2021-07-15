package com.hansung.traveldiary.src.profile.edit_info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var getResultImage : ActivityResultLauncher<Intent>
    private lateinit var imagePath : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)


        Glide.with(this).load(ResourcesCompat.getDrawable(resources, R.drawable.img_bg_profile, null)).circleCrop().into(binding.editIvProfile)
        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
                if(result.resultCode == RESULT_OK){
                    imagePath = result.data?.getStringExtra("imagePath")!!
                    Glide.with(this).load(imagePath).circleCrop().into(binding.editIvProfile)
                }
            }

        binding.editIvProfile.setOnClickListener{
            getResultImage.launch(Intent(this@EditInfoActivity, SelectPictureActivity::class.java))
        }


    }
}
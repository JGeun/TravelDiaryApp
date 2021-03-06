package com.hansung.traveldiary.src.diary.write_diary.add_image

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.hansung.traveldiary.databinding.ActivitySelectPictureBinding
import com.hansung.traveldiary.src.profile.gallery.GalleryAdapter
import com.hansung.traveldiary.src.profile.gallery.ImageGallery
import com.hansung.traveldiary.util.StatusBarUtil

class AddPictureActivity : AppCompatActivity() {
    private val MY_READ_PERMISSION_CODE = 101
    private val binding : ActivitySelectPictureBinding by lazy{
        ActivitySelectPictureBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        binding.spIvBack.setOnClickListener{
            finish()
        }
        //check from permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_READ_PERMISSION_CODE)
        } else {
            loadImage()
        }

    }

    private fun loadImage(){
        val images = ImageGallery.listOfImages(this)
        binding.spRvGalleryImages.apply{
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = GalleryAdapter(this@AddPictureActivity, images)
        }
    }
}
package com.hansung.traveldiary.src.profile.gallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.hansung.traveldiary.databinding.ActivitySelectPictureBinding
import com.hansung.traveldiary.util.StatusBarUtil


class SelectPictureActivity : AppCompatActivity() {
    private val MY_READ_PERMISSION_CODE = 101
    private val binding : ActivitySelectPictureBinding by lazy{
        ActivitySelectPictureBinding.inflate(layoutInflater)
    }
    private var images = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        binding.spIvBack.setOnClickListener{
            finish()
        }

        binding.spRvGalleryImages.apply{
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = GalleryAdapter(this@SelectPictureActivity, images)
        }

        //check from permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_READ_PERMISSION_CODE
            )
        } else {
            loadImage()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (Build.VERSION.SDK_INT >= 23) {

            // requestPermission의 배열의 index가 아래 grantResults index와 매칭
            // 퍼미션이 승인되면
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImage()

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                finish()
                // TODO : 퍼미션이 거부되는 경우에 대한 코드
            }
        }
    }
    private fun loadImage(){
        images = ImageGallery.listOfImages(this)
        binding.spRvGalleryImages.apply{
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = GalleryAdapter(this@SelectPictureActivity, images)
        }
        binding.spRvGalleryImages.adapter!!.notifyDataSetChanged()
    }
}
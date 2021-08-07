package com.hansung.traveldiary.src.diary.write_diary.show_plan

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityDiaryImageEditBinding
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity

class DiaryImageEditActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDiaryImageEditBinding.inflate(layoutInflater)
    }

    private val diaryImageList = ArrayList<Drawable>()
    private lateinit var getResultImage: ActivityResultLauncher<Intent>
    private lateinit var imagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        initdiaryImageList()

        binding.editImageRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EditImageAdapter(diaryImageList)
            setHasFixedSize(true)
        }

        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.d("갤러리", "OK")
                imagePath = result.data?.getStringExtra("imagePath")!!
                var bitmap = BitmapFactory.decodeFile(imagePath, BitmapFactory.Options())
                var drawable = BitmapDrawable(bitmap)
                diaryImageList.add(drawable)
                binding.editImageRv.adapter?.notifyDataSetChanged()
            }
        }

        binding.addImageBtn.setOnClickListener {
            getResultImage.launch(Intent(this, SelectPictureActivity::class.java))
        }

        var index = intent.getIntExtra("index", 0)
        var day = intent.getIntExtra("day", 0)
        Log.d("인덱스, 데이", "$index / $day")
        binding.tvChecked.setOnClickListener {
            //이미지 메인 데이터에 넣고
            for(i in 0..diaryImageList.size-1) {
//                var imageUri = "drawable://" + diaryImageList.get(i)
//                MainActivity.myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList[day - 1].diaryInfo.imagePathArray.add(imageUri)
            }
            finish()
        }

    }
    private fun initdiaryImageList(){
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul, null)!!)
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)!!)
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_gangneung, null)!!)
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul_nearby, null)!!)
    }
}
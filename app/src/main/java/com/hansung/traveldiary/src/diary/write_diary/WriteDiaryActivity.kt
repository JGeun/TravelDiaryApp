package com.hansung.traveldiary.src.diary.write_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
<<<<<<< HEAD
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.FirebaseFirestoreException
=======
>>>>>>> jg_branch
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityWriteDiaryBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.util.StatusBarUtil

class WriteDiaryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWriteDiaryBinding.inflate(layoutInflater)
    }

    private val viewModel : SelectDayViewModel by viewModels()
    private var index = 0
    private var day = 0

    companion object{
        val fragmentArray = ArrayList<WriteDayDiaryFragment>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        index = intent.getIntExtra("index", 0)
        day = intent.getIntExtra("day", 0)

        viewModel.dayData.observe(this){
            println("들어옴")
            val transaction2 = supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[viewModel.dayData.value!!])
            transaction2.commit()
        }

        binding.writeDiaryTitle.setText(MainActivity.userDiaryArray[index].baseData.title)

        binding.writeDiaryIvCancle.setOnClickListener {
            finish()
        }

        binding.showPlacelist.setOnClickListener {
            var intent = Intent(it.context, ShowPlacelistActivity::class.java)
            intent.putExtra("index", index)
            startActivity(intent)
        }

    }
    override fun onStart() {
        super.onStart()
        fragmentArray.clear()

        for (i in 0 until MainActivity.userDiaryArray[index].diaryArray.size) {
            fragmentArray.add(WriteDayDiaryFragment(index, i))
        }

        viewModel.setDay(day)
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[viewModel.dayData.value!!])
        transaction.commit()
    }
}




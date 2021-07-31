package com.hansung.traveldiary.src.diary.write_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityWriteDiaryBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.util.StatusBarUtil

class WriteDiaryActivity : AppCompatActivity() {
    var viewList = ArrayList<View>()
    private val fragmentArray = ArrayList<WriteDayDiaryFragment>()

    private val binding by lazy {
        ActivityWriteDiaryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        val index = intent.getIntExtra("index", 0)
        val day = intent.getIntExtra("day", 0)
        //날짜 수에 따른 스피너 동적 생성
        var spinnerArray = arrayListOf<String>()

        for (i in 1..MainActivity.myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList.size) {
            spinnerArray.add("Day-${i}")
            fragmentArray.add(WriteDayDiaryFragment(MainActivity.myDiaryList[index].diaryData.diaryInfoFolder, i-1))
        }

        binding.uploadDiarySpinner.adapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray)
        //스피너 선택에 따른 화면전환
        var transaction =
            supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[0])
        transaction.commit()
        binding.uploadDiarySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //스피너를 클릭하면서 발생하는 화면 전환
                    transaction = supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragmentArray[position])
                    transaction.commit()
                    println(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    transaction = supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragmentArray[0])
                    transaction.commit()
                    println("nothing 0")
                }
            }

    }
}




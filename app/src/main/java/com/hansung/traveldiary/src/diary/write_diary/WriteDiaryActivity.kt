package com.hansung.traveldiary.src.diary.write_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityWriteDiaryBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.util.StatusBarUtil

class WriteDiaryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWriteDiaryBinding.inflate(layoutInflater)
    }

    private val viewModel : WriteViewModel by viewModels()

    companion object{
        val fragmentArray = ArrayList<WriteDayDiaryFragment>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        val index = intent.getIntExtra("index", 0)
        val day = intent.getIntExtra("day", 0)
        //날짜 수에 따른 스피너 동적 생성

        for (i in 1..MainActivity.myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList.size) {
            fragmentArray.add(WriteDayDiaryFragment(MainActivity.myDiaryList[index].diaryData.diaryInfoFolder, i-1, index))
        }

        println("onCreate")
        var transaction =
            supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[0])
        transaction.commit()

        viewModel.data.observe(this){
            println("observe")
            println("value: ${viewModel.data.value}")
            var transaction =
                supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[viewModel.data.value!!])
            transaction.commit()
        }
//        binding.uploadDiarySpinner.adapter =
//            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray)
//        //스피너 선택에 따른 화면전환

//        transaction.commit()
//        binding.uploadDiarySpinner.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    //스피너를 클릭하면서 발생하는 화면 전환
//                    transaction = supportFragmentManager.beginTransaction()
//                        .replace(R.id.framelayout, fragmentArray[position])
//                    transaction.commit()
//                    println(position)
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    transaction = supportFragmentManager.beginTransaction()
//                        .replace(R.id.framelayout, fragmentArray[0])
//                    transaction.commit()
//                    println("nothing 0")
//                }
//            }

    }
}




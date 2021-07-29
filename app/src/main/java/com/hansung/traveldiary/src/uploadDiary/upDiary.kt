package com.hansung.traveldiary.src.uploadDiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.PagerAdapter
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.UploadDiaryBinding

class upDiary : AppCompatActivity() {
    var viewList = ArrayList<View>()
    private val fr_days = Day1()
    private val fr_day2=Day2()
    private val fr_day3=Day3()
    private val fr_day4=Day4()
    private val fr_day5=Day5()
    private val fr_day6=Day6()
    private val fr_day7=Day7()

    override fun onCreate(savedInstanceState: Bundle?) {
        var binding = UploadDiaryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //날짜 수에 따른 스피너 동적 생성
        var spinnerArray = arrayListOf<String>()
        var day = 7
        var count_days = 0
        for (i in 1..day) {
            spinnerArray.add("Day-${i}")
        }
        binding.uploadDiarySpinner.adapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray)
        //스피너 선택에 따른 화면전환
        var transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout, fr_days)
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
                    when (position+1) {
                        1->{
                            transaction=supportFragmentManager.beginTransaction().replace(R.id.framelayout,fr_days)
                            transaction.commit()

                        }
                        2->{
                            transaction=supportFragmentManager.beginTransaction().replace(R.id.framelayout,fr_day2)
                            transaction.commit()
                        }
                        3->{
                            transaction=supportFragmentManager.beginTransaction().replace(R.id.framelayout,fr_day3)
                            transaction.commit()
                        }
                        4->{
                            transaction=supportFragmentManager.beginTransaction().replace(R.id.framelayout,fr_day4)
                            transaction.commit()
                        }
                        5->{
                            transaction=supportFragmentManager.beginTransaction().replace(R.id.framelayout,fr_day5)
                            transaction.commit()
                        }
                        6->{
                        transaction=supportFragmentManager.beginTransaction().replace(R.id.framelayout,fr_day6)
                        transaction.commit()
                        }
                        7->{
                            transaction=supportFragmentManager.beginTransaction().replace(R.id.framelayout,fr_day7)
                            transaction.commit()
                        }


                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    return
                }
            }

    }
}




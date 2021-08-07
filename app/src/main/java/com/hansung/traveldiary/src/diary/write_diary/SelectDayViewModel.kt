package com.hansung.traveldiary.src.diary.write_diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectDayViewModel : ViewModel(){
    var dayData : MutableLiveData<Int> = MutableLiveData()

    init{
        dayData.value = 0
    }

    fun setDay(day : Int){
        dayData.value = day
    }
}
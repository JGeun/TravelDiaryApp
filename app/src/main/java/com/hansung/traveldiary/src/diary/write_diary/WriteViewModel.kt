package com.hansung.traveldiary.src.diary.write_diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteViewModel : ViewModel(){
    var data : MutableLiveData<Int> = MutableLiveData()

    init{
        data.value = 0
    }

    fun setData(pos : Int){
        data.value = pos
    }
}
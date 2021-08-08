package com.hansung.traveldiary.src.diary.write_diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel : ViewModel(){
    val imageCount : MutableLiveData<Int> = MutableLiveData()

    init{
        imageCount.value = 0
    }

    fun setCount(count: Int){
        imageCount.value = count
    }

    fun countUp(){
        imageCount.value = imageCount.value?.plus(1)
    }

    fun countDown(){
        imageCount.value = imageCount.value?.minus(1)
    }
}
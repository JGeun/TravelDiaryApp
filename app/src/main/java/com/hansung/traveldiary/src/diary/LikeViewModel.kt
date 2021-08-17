package com.hansung.traveldiary.src.diary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LikeViewModel : ViewModel(){
    val likeCount : MutableLiveData<Int> = MutableLiveData()

    init{
        likeCount.value = 0
    }

    fun setCount(count: Int){
        likeCount.value = count
    }

    fun countUp(){
        likeCount.value = likeCount.value?.plus(1)
    }

    fun countDown(){
        likeCount.value = likeCount.value?.minus(1)
    }
}
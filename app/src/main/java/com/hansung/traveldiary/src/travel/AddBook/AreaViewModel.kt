package com.hansung.traveldiary.src.travel.AddBook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AreaViewModel : ViewModel() {
    val areaData : MutableLiveData<String> = MutableLiveData()

    init {
        areaData.value = ""
    }

    fun setArea(area : String){
        areaData.value = area
    }
}
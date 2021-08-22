package com.hansung.traveldiary.src.bulletin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchWordVIewModel : ViewModel(){
    val searchWord : MutableLiveData<String> = MutableLiveData()

    init{
        searchWord.value= "값 들어오기 전"
    }

}
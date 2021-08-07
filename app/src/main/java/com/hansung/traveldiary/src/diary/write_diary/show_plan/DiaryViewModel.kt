package com.hansung.traveldiary.src.diary.write_diary.show_plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiaryViewModel: ViewModel() {
    var content: MutableLiveData<String> = MutableLiveData()
    init{
        content.value=""
    }

    fun updaatecontent(contents:String){
        content.value=contents
    }
}
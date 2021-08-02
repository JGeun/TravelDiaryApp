package com.hansung.traveldiary.src.diary

import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansung.traveldiary.R

class DiaryDayViewModel: ViewModel() {
    val imageData : MutableLiveData<Int> = MutableLiveData()


    init{
        imageData.value = R.drawable.ic_edit
    }

    fun change(){
        if(imageData.value == R.drawable.ic_find_black){
            imageData.value = R.drawable.ic_edit
        }else{
            imageData.value = R.drawable.ic_find_black
        }
    }
}
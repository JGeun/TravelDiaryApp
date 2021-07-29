package com.hansung.traveldiary.src.uploadDiary

import android.content.Context

class DiaryDTO (
    var context: String,
    var image:String
){
    fun getImageId(context: Context):Int{
        return context.resources.getIdentifier(image,"drawable",context.packageName)
    }
}
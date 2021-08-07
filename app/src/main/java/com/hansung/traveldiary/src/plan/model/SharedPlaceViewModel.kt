package com.hansung.traveldiary.src.plan.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansung.traveldiary.src.PlaceData
import com.hansung.traveldiary.src.PlaceInfo

class SharedPlaceViewModel : ViewModel() {
    val userPlanData : MutableLiveData<PlaceInfo> = MutableLiveData()
    var items = PlaceInfo()

    init {
        userPlanData.value = items
    }

    fun putAllData(data : PlaceInfo){
        items = data
        userPlanData.value = items
    }

    fun putPlace(place : PlaceData){
        items.placeFolder.add(place)
        userPlanData.value = items
    }

    fun removePlace(index: Int){
        Log.d("장소 index", index.toString())
        items.placeFolder.removeAt(index)
        userPlanData.value = items
    }

    fun moveUp(index: Int){
        val tmp = items.placeFolder[index]
        items.placeFolder[index] = items.placeFolder[index-1]
        items.placeFolder[index-1] = tmp
        userPlanData.value = items
    }

    fun moveDown(index: Int){
        val tmp = items.placeFolder[index]
        items.placeFolder[index] = items.placeFolder[index+1]
        items.placeFolder[index+1] = tmp
        userPlanData.value = items
    }
}
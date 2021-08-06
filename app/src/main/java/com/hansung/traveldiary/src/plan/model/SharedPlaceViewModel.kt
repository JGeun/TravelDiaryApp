package com.hansung.traveldiary.src.plan.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansung.traveldiary.src.PlaceDayInfo2
import com.hansung.traveldiary.src.PlaceInfo2
import com.hansung.traveldiary.src.PlaceInfoFolder2

class SharedPlaceViewModel : ViewModel() {
    val userPlanData : MutableLiveData<PlaceInfoFolder2> = MutableLiveData()
    var items = PlaceInfoFolder2()

    init {
        userPlanData.value = items
    }

    fun putAllData(data : PlaceInfoFolder2){
        items = data
        userPlanData.value = items
    }
    fun putPlace(place : PlaceInfo2, index: Int){
        items.dayPlaceList[index].placeFolder.add(place)
        userPlanData.value = items
    }

    fun putPlaceDayInfo(placeDayInfo : PlaceDayInfo2){
        items.dayPlaceList.add(placeDayInfo)
        userPlanData.value = items
    }

    fun removePlace(index: Int, position : Int){
        Log.d("장소 index", position.toString())
        items.dayPlaceList[index].placeFolder.removeAt(position)
        userPlanData.value = items
    }

    fun moveUp(index: Int, position: Int){
        val tmp = items.dayPlaceList[index].placeFolder[position]
        items.dayPlaceList[index].placeFolder[position] = items.dayPlaceList[index].placeFolder[position-1]
        items.dayPlaceList[index].placeFolder[position-1] = tmp
        userPlanData.value = items
    }

    fun moveDown(index: Int, position: Int){
        val tmp = items.dayPlaceList[index].placeFolder[position]
        items.dayPlaceList[index].placeFolder[position] = items.dayPlaceList[index].placeFolder[position+1]
        items.dayPlaceList[index].placeFolder[position+1] = tmp
        userPlanData.value = items
    }

}
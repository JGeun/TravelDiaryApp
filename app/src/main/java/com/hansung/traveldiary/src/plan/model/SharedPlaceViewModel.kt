package com.hansung.traveldiary.src.plan.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansung.traveldiary.src.PlaceDayInfo
import com.hansung.traveldiary.src.PlaceInfo
import com.hansung.traveldiary.src.PlaceInfoFolder

class SharedPlaceViewModel : ViewModel() {
    val userPlanData : MutableLiveData<PlaceInfoFolder> = MutableLiveData()
    var items = PlaceInfoFolder()

    init {
        userPlanData.value = items
    }

    fun putAllData(data : PlaceInfoFolder){
        items = data
        userPlanData.value = items
    }
    fun putPlace(place : PlaceInfo, index: Int){
        items.dayPlaceList[index].placeInfoArray.add(place)
        userPlanData.value = items
    }

    fun putPlaceDayInfo(placeDayInfo : PlaceDayInfo){
        items.dayPlaceList.add(placeDayInfo)
        userPlanData.value = items
    }

    fun removePlace(index: Int, position : Int){
        Log.d("장소 index", position.toString())
        items.dayPlaceList[index].placeInfoArray.removeAt(position)
        userPlanData.value = items
    }

    fun moveUp(index: Int, position: Int){
        val tmp = items.dayPlaceList[index].placeInfoArray[position]
        items.dayPlaceList[index].placeInfoArray[position] = items.dayPlaceList[index].placeInfoArray[position-1]
        items.dayPlaceList[index].placeInfoArray[position-1] = tmp
        userPlanData.value = items
    }

    fun moveDown(index: Int, position: Int){
        val tmp = items.dayPlaceList[index].placeInfoArray[position]
        items.dayPlaceList[index].placeInfoArray[position] = items.dayPlaceList[index].placeInfoArray[position+1]
        items.dayPlaceList[index].placeInfoArray[position+1] = tmp
        userPlanData.value = items
    }

}
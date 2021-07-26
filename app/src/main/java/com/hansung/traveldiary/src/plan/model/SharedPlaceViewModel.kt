package com.hansung.traveldiary.src.plan.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedPlaceViewModel : ViewModel() {
    val userPlanData : MutableLiveData<ArrayList<PlaceInfo>> = MutableLiveData()
    var items = ArrayList<PlaceInfo>()

    init {
        userPlanData.value = items
    }

    fun putPlaceAll(data : ArrayList<PlaceInfo>){
        items = data
        userPlanData.value = items
    }
    fun putPlace(data : PlaceInfo){
        items.add(data)
        userPlanData.value = items
    }

    fun removePlace(index: Int){
        items.removeAt(index)
        userPlanData.value = items
    }

    fun printPlace(){
        for(data in userPlanData.value!!)
            println(data.placeName)
    }

    fun getCount() : Int{
        return items.size
    }
}
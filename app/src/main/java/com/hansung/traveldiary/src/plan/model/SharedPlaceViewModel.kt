package com.hansung.traveldiary.src.plan.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.map.overlay.Marker

data class PlaceData(var location : String, var latitude : Double, var longitude: Double)

class SharedPlaceViewModel : ViewModel() {
    val userPlaceData : MutableLiveData<ArrayList<PlaceData>> = MutableLiveData()
    val items = ArrayList<PlaceData>()

    init {
        userPlaceData.value = items
    }
    fun putPlace(data : PlaceData){
        items.add(data)
        userPlaceData.value = items
    }

    fun removePlace(index: Int){
        items.removeAt(index)
        userPlaceData.value = items
    }

    fun printPlace(){
        for(data in userPlaceData.value!!)
            println(data.location)
    }

    fun getCount() : Int{
        return items.size
    }
}
package com.hansung.traveldiary.src.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AirportViewModel : ViewModel() {
    val airport : MutableLiveData<String> = MutableLiveData()
    val destinationAirPort:MutableLiveData<String> = MutableLiveData()
    init {
        airport.value = "출발지"
        destinationAirPort.value="목적지"
    }

    fun setArea(area : String){
        airport.value = area
    }
    fun setDestinationArea(area:String){
        destinationAirPort.value=area
    }
}
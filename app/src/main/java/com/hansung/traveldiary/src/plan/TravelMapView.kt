package com.hansung.traveldiary.src.plan

import com.hansung.traveldiary.src.plan.model.MapSearchInfo

interface TravelMapView{
    fun onGetMapSearchSuccess(response: MapSearchInfo)
    fun onGetMapSearchFailure(message: String)
}
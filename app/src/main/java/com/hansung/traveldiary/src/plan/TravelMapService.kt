package com.hansung.traveldiary.src.plan

import com.hansung.traveldiary.src.home.model.WeatherInfo
import com.hansung.traveldiary.src.plan.model.MapSearchInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TravelMapService(val view: TravelMapView){
    fun tryGetSearchInfo(query:String, display: Int, start: Int, sort: String){
        val searchRetroInterface : TravelMapRetroInterface = TravelDiaryActivity.retrofit.create(TravelMapRetroInterface::class.java)
        searchRetroInterface.getSearchInfo(query, display, start, sort).enqueue(object: Callback<MapSearchInfo>{
            override fun onResponse(call: Call<MapSearchInfo>, response: Response<MapSearchInfo>) {
                view.onGetMapSearchSuccess(response.body() as MapSearchInfo)
            }

            override fun onFailure(call: Call<MapSearchInfo>, t: Throwable) {
                view.onGetMapSearchFailure(t.message ?: "통신 오류")
            }
        })
    }
}
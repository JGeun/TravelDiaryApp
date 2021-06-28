package com.hansung.traveldiary.src.home

import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.home.model.WeatherInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import retrofit2.http.GET

class WeatherService(val view: HomeView) {
    fun tryGetWeatherInfo(){
        val weatherRetrofitInterface = MainActivity.retrofit.create(WeatherRetrofitInterface::class.java)
        weatherRetrofitInterface.getWeatherInfo().enqueue(object: Callback<WeatherInfo>{
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                view.onGetWeatherInfoSuccess(response.body() as WeatherInfo)
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                view.onGetUserInfoFailure(t.message ?: "통신 오류")
            }
        })
    }
}
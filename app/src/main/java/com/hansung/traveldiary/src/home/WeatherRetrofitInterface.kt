package com.hansung.traveldiary.src.home

import com.hansung.traveldiary.src.home.model.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET

interface WeatherRetrofitInterface {
    @GET("onecall?lat=30.489772&lon=-99.771335&units=metric&lang=kr&exclude=minutely,-,hourly,alerts&appid=58dee976f04bcaec9e95f50d8da2a752")
    fun getWeatherInfo() : Call<WeatherInfo>
}
package com.hansung.traveldiary.src.home

import com.hansung.traveldiary.src.home.model.WeatherInfo

interface HomeView {
    fun onGetWeatherInfoSuccess(respose: WeatherInfo)
    fun onGetUserInfoFailure(message: String)
}
package com.hansung.traveldiary.src.home.model

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezome") val timezome: String,
    @SerializedName("timezone_offset") val timezone_offset: Int,
    @SerializedName("current") val current: CurrentData,
    @SerializedName("daily") val daily: ArrayList<DailyData>
)

data class CurrentData(
    @SerializedName("dt") val dt: Long,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("temp") val temp: Float,
    @SerializedName("feels_like") val feels_like: Float,
    @SerializedName("pressure") val pressure: Float,
    @SerializedName("humidity") val humidity: Float,
    @SerializedName("dew_point") val dew_point: Float,
    @SerializedName("uvi") val uvi: Float,
    @SerializedName("clouds") val clouds: Float,
    @SerializedName("visibility") val visibility: Float,
    @SerializedName("wind_speed") val wind_speed: Float,
    @SerializedName("wind_deg") val wind_deg: Float,
    @SerializedName("weather") val weather: ArrayList<CurrentWeatherData>
)

data class CurrentWeatherData(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String,
)

data class DailyData(
    @SerializedName("dt") val dt: Long,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("moonrise") val moonrise: Long,
    @SerializedName("moonset") val moonset: Long,
    @SerializedName("moon_phase") val moon_phase: Float,
    @SerializedName("temp") val temp: TempData,
    @SerializedName("feels_like") val feels_like: FeelsLikeData,
    @SerializedName("pressure") val pressure: Float,
    @SerializedName("humidity") val humidity: Float,
    @SerializedName("dew_point") val dew_point: Float,
    @SerializedName("wind_speed") val wind_speed: Float,
    @SerializedName("wind_deg") val wind_deg: Float,
    @SerializedName("wind_gust") val wind_gust: Float,
    @SerializedName("weather") val weather: ArrayList<CurrentWeatherData>,
    @SerializedName("clouds") val clouds: Float,
    @SerializedName("pop") val pop: Float,
    @SerializedName("rain") val rain: Float,
    @SerializedName("uvi") val uvi: Float,
    )

data class TempData(
    @SerializedName("day") val day: Float,
    @SerializedName("min") val min: Float,
    @SerializedName("max") val max: Float,
    @SerializedName("night") val night: Float,
    @SerializedName("eve") val eve: Float,
    @SerializedName("morn") val morn: Float,

)

data class FeelsLikeData(
    @SerializedName("day") val day: Float,
    @SerializedName("night") val night: Float,
    @SerializedName("eve") val eve: Float,
    @SerializedName("morn") val morn: Float,
)
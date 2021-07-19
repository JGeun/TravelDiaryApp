package com.hansung.traveldiary.src.plan

import com.hansung.traveldiary.src.plan.model.MapSearchInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TravelMapRetroInterface {
    @Headers(
        "X-Naver-Client-Id: 5bDIUeQSU3t5aWf4ZZhZ",
        "X-Naver-Client-Secret: EsuavJrDvq"
    )
    @GET("search/local.json")
    fun getSearchInfo(@Query("query") query: String, @Query("target") target: String, @Query("display") display: Int, @Query("start") start: Int, @Query("sort") sort: String) : Call<MapSearchInfo>
}
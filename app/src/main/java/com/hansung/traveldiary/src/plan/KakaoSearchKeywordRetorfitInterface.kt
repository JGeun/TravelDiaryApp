package com.hansung.traveldiary.src.plan

import com.hansung.traveldiary.src.plan.model.KakaoSearchKeywordResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoSearchKeywordRetorfitInterface {
    @Headers(
        "Authorization: KakaoAK e7e5930e1663be60ef61212260e4a4d7"
    )
    @GET("local/search/keyword.json")
    fun getKeywordSearchInfo(@Query("query") query: String, @Query("page") page: Int) : Call<KakaoSearchKeywordResponse>
}
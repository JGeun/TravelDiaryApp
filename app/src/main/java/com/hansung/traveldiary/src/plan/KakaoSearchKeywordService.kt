package com.hansung.traveldiary.src.plan

import com.hansung.traveldiary.src.plan.model.KakaoSearchKeywordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoSearchKeywordService(val view: KakaoSearchView){
    fun tryGetKeyWordSearchInfo(query:String){
        val kakaoKeywordSearchRetrofitInterface : KakaoSearchKeywordRetorfitInterface = TravelPlanActivity.kakaoRetrofit.create(KakaoSearchKeywordRetorfitInterface::class.java)
        kakaoKeywordSearchRetrofitInterface.getKeywordSearchInfo(query).enqueue(object : Callback<KakaoSearchKeywordResponse>{
            override fun onResponse(call: Call<KakaoSearchKeywordResponse>, response: Response<KakaoSearchKeywordResponse>) {
                view.onGetKeywordSearchSuccess(response.body() as KakaoSearchKeywordResponse)
            }

            override fun onFailure(call: Call<KakaoSearchKeywordResponse>, t: Throwable) {
                view.onGetKeywordSearchFailure(t.message ?: "통신 오류")
            }

        })
    }
}
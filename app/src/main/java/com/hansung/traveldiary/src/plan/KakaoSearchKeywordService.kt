package com.hansung.traveldiary.src.plan

import com.hansung.traveldiary.src.plan.model.KakaoSearchKeywordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoSearchKeywordService(val view: KakaoSearchView){
    fun tryGetKeyWordSearchInfo(query:String, page: Int){
        val kakaoKeywordSearchRetrofitInterface : KakaoSearchKeywordRetorfitInterface = TravelPlanBaseActivity.kakaoRetrofit.create(KakaoSearchKeywordRetorfitInterface::class.java)
        kakaoKeywordSearchRetrofitInterface.getKeywordSearchInfo(query, page).enqueue(object : Callback<KakaoSearchKeywordResponse>{
            override fun onResponse(call: Call<KakaoSearchKeywordResponse>, response: Response<KakaoSearchKeywordResponse>) {
                if(response.body() != null)
                    view.onGetKeywordSearchSuccess(response.body() as KakaoSearchKeywordResponse)
                else
                    view.onGetKeywordSearchSuccess(null)
            }

            override fun onFailure(call: Call<KakaoSearchKeywordResponse>, t: Throwable) {
                view.onGetKeywordSearchFailure(t.message ?: "통신 오류")
            }
        })
    }
}
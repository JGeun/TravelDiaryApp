package com.hansung.traveldiary.src.plan

import com.hansung.traveldiary.src.plan.model.KakaoSearchKeywordResponse
import com.hansung.traveldiary.src.plan.model.MapSearchInfo

interface KakaoSearchView {
    fun onGetKeywordSearchSuccess(response: KakaoSearchKeywordResponse)
    fun onGetKeywordSearchFailure(message: String)
}
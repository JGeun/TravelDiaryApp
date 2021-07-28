package com.hansung.traveldiary.src.plan.model

import com.google.gson.annotations.SerializedName

data class KakaoSearchKeywordInfo(
    @SerializedName("address_name") val address_name: String,
    @SerializedName("category_group_code") val category_group_code: String,
    @SerializedName("category_group_name") val category_group_name: String,
    @SerializedName("category_name") val category_name: String,
    @SerializedName("id") val id: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("place_name") val place_name: String,
    @SerializedName("place_url") val place_url: String,
    @SerializedName("road_address_name") val road_address_name: String,
    @SerializedName("x") val x: String,
    @SerializedName("y") val y: String
)

data class KakaoSearchMetaInfo(
    @SerializedName("is_end") val is_end : Boolean,
    @SerializedName("pageable_count") val pageable_count: Int
)

data class KakaoSearchKeywordResponse(
    @SerializedName("documents") val documents: ArrayList<KakaoSearchKeywordInfo>,
    @SerializedName("meta") val meta: KakaoSearchMetaInfo
)
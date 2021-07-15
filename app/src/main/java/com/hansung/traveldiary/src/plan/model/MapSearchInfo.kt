package com.hansung.traveldiary.src.plan.model

import com.google.gson.annotations.SerializedName

data class SearchInfo(
    @SerializedName("title") val title: String,
    @SerializedName("link")  val link : String,
    @SerializedName("category") val category : String,
    @SerializedName("description") val description : String,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("address") val address : String,
    @SerializedName("roadAddress") val roadAddress: String,
    @SerializedName("mapx") val mapx: Int,
    @SerializedName("mapy") val mapy : Int
)

data class MapSearchInfo (
    @SerializedName("lastBuildDate") val lastBuildDate: String,
    @SerializedName("total") val total : Int,
    @SerializedName("start") val start: Int,
    @SerializedName("display") val display : Int,
    @SerializedName("items") val item : ArrayList<SearchInfo>
)
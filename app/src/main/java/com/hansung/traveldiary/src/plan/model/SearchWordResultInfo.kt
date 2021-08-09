package com.hansung.traveldiary.src.plan.model

import java.io.Serializable

class SearchWordResultInfo(placeName: String, address: String, groupCode:String, categoryName: String, roadAddress: String, phone:String, url:String,
longitude: String, latitude: String) : Serializable {
    var placeName : String
    var address : String
    var groupCode:String
    var categoryName: String
    var roadAddress: String
    var phone: String
    var url: String
    var longitude: String
    var latitude: String

    init{
        this.placeName = placeName
        this.address = address
        this.groupCode=groupCode
        this.categoryName = categoryName
        this.roadAddress = roadAddress
        this.phone = phone
        this.url  = url
        this.longitude = longitude
        this.latitude = latitude
    }
}


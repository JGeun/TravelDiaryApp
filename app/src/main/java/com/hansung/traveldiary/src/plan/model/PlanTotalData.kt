package com.hansung.traveldiary.src.plan.model

import java.io.Serializable

data class PlaceInfo(var placeName: String? = null, var latitude: Double? = null, var longitude: Double? = null)
data class DayInfo(var date: String = "", var placeInfoArray : ArrayList<PlaceInfo> = ArrayList<PlaceInfo>())

//class PlanTotalData(){
//    var color : String
//    var startDate : String
//    var endDate : String
//    var dayList : ArrayList<DayInfo>
//
//    constructor(color: String, startDate: String, endDate: String, dayList: ArrayList<DayInfo>) : this() {
//        this.color = color
//        this.startDate = startDate
//        this.endDate = endDate
//        this.dayList = dayList
//    }
//
//    init{
//        this.dayList = ArrayList<DayInfo>()
//        this.startDate = "2021-07-26"
//        this.endDate = "2021-09-30"
//        this.color = "red"
//    }
//}

class PlanTotalData(
    var color: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var dayList: ArrayList<DayInfo> = ArrayList<DayInfo>()
)


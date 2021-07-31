package com.hansung.traveldiary.src

//Common
data class UserEmailList(var emailFolder: ArrayList<String> = ArrayList())
data class TitleList(var titleFolder: ArrayList<String> = ArrayList())

data class PlaceInfo(var placeName: String? = null, var latitude: Double = 37.58842461354086, var longitude: Double = 127.00601781685579)
data class PlaceDayInfo(var date: String = "", var placeInfoArray : ArrayList<PlaceInfo> = ArrayList<PlaceInfo>())
data class PlaceInfoFolder(var dayPlaceList: ArrayList<PlaceDayInfo> = ArrayList())

//DiaryDataSet
data class DiaryBulletinData(var planTitle: String, var diaryData: DiaryData)

data class DiaryBaseData(var title: String="", var mainImage: String="", var nickName: String="", var uploadDate: String="",
                         var locationTag: String="", var like: Int=0, var comments: Int=0)

data class DiaryData(
    var planBaseData: PlanBaseData = PlanBaseData(),
    var diaryBaseData: DiaryBaseData = DiaryBaseData(),
    var placeFolder : PlaceInfoFolder =PlaceInfoFolder(),
    var diaryInfoFolder: DiaryInfoFolder = DiaryInfoFolder()
)

data class DiaryInfoFolder(var diaryDayList : ArrayList<DiaryDayInfo> = ArrayList())

data class DiaryDayInfo(var date : String ="", var diaryInfo : DiaryInfo = DiaryInfo())

data class DiaryInfo(
    var imagePathArray : ArrayList<String> = ArrayList(),
    var diaryTitle : String = "",
    var diaryContents : String = ""
)

//PlanDataSet
data class PlanBaseData(var color: String = "", var startDate: String = "", var endDate: String = "", var area: String="서울", var peopleCount: Int= 1)

data class PlanData(
    var planBaseData: PlanBaseData = PlanBaseData(),
    var placeFolder: PlaceInfoFolder = PlaceInfoFolder()
)

data class PlanBookData(var title: String, var planData: PlanData)


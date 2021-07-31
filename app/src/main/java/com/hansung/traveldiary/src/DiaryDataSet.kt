package com.hansung.traveldiary.src

data class DiaryBulletinData(var diaryData: DiaryData)

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
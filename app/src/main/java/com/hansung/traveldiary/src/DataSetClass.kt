package com.hansung.traveldiary.src


data class TitleList(var titleFolder: ArrayList<String> = ArrayList())

data class PlaceInfo2(var placeName: String? = null, var latitude: Double = 37.58842461354086, var longitude: Double = 127.00601781685579)
data class PlaceDayInfo2(var date: String = "", var placeFolder : ArrayList<PlaceInfo2> = ArrayList<PlaceInfo2>())
data class PlaceInfoFolder2(var dayPlaceList: ArrayList<PlaceDayInfo2> = ArrayList())

//DiaryDataSet
data class DiaryBulletinData(var planTitle: String, var diaryData: DiaryData)

data class DiaryBaseData(var title: String="", var mainImage: String="", var userName: String="", var userImage: String="",var uploadDate: String="",
                         var locationTag: String="", var like: Int=0, var comments: Int=0)

data class DiaryData(
    var planBaseData: PlanBaseData2 = PlanBaseData2(),
    var diaryBaseData: DiaryBaseData = DiaryBaseData(),
    var placeFolder : PlaceInfoFolder2 =PlaceInfoFolder2(),
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
data class PlanBaseData2(var title: String="", var color: String = "", var startDate: String = "", var endDate: String = "", var area: String="서울", var peopleCount: Int= 1)

data class PlanData(
    var planBaseData: PlanBaseData2 = PlanBaseData2(),
    var placeFolder: PlaceInfoFolder2 = PlaceInfoFolder2()
)

data class PlanBookData(var title: String, var planData: PlanData)

//UserInfoDataSet
data class UserContents(var nickname: String="", var profileImage: String="")

//------------------------앞으로 사용할 것 ------------------------------------------
//Total User Email List
data class UserList(var emailFolder: ArrayList<String> = ArrayList())

//Total Idx List
data class IdxList(var idxFolder: ArrayList<Long> = ArrayList())


////UserInfoDataSet
//data class UserContents(var nickname: String="", var profileImage: String="")
//
data class PlaceData(var latitude: Double = 37.58842461354086, var longitude: Double = 127.00601781685579, var placeName: String? = null)
data class PlaceInfo(var placeFolder : ArrayList<PlaceData> = ArrayList())
data class PlanBaseData(var idx: Long = 0, var title: String="", var color: String = "", var startDate: String = "", var endDate: String = "", var area: String="서울", var peopleCount: Int= 1)

data class UserPlanData(var planBaseData: PlanBaseData = PlanBaseData(), var placeArray : ArrayList<PlaceInfo> = ArrayList()) : Comparable<UserPlanData>{
    override fun compareTo(other: UserPlanData): Int {
        if(this.planBaseData.startDate.compareTo(other.planBaseData.startDate) < 0){
            return -1
        }else if(this.planBaseData.startDate.compareTo(other.planBaseData.startDate) > 0){
            return 1
        }else{
            return this.planBaseData.endDate.compareTo(other.planBaseData.endDate)
        }
    }

}

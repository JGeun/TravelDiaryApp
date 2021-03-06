package com.hansung.traveldiary.src

///---------앞으로 사용할 것-----------------------------------------------

//Total User Email List
data class UserList(var emailFolder: ArrayList<String> = ArrayList())
data class FriendList(var friendFolder: ArrayList<String> = ArrayList())
data class UserInfo(var email: String = "", var nickname:String= "", var profileImage: String= "", var friendList : FriendList = FriendList())

//Total Idx List
data class IdxList(var idxFolder: ArrayList<Long> = ArrayList())

//Chat
data class ChatIdxFolder(var chatIdxFolder: ArrayList<ChatIdxData> = ArrayList())
data class ChatIdxData(var idx: Long = 0, var friendsEmailList : UserList = UserList(), var title: String = "", var image: String = "", var preview: String = "", var lastTime: String = "")

data class ChatFolder(var chatFolder: ArrayList<ChatData> = ArrayList())
data class ChatData(var userEmail: String ="", var contents: String="")

////UserInfoDataSet
//data class UserContents(var nickname: String="", var profileImage: String="")
data class PlaceData(var latitude: Double = 37.58842461354086, var longitude: Double = 127.00601781685579, var placeName: String? = null)
data class PlaceInfo(var placeFolder : ArrayList<PlaceData> = ArrayList())
data class PlanBaseData(var idx: Long = 0, var title: String="", var color: String = "", var startDate: String = "", var endDate: String = "", var area: String="서울", var friendsList: FriendList = FriendList())

data class UserPlanData(var baseData: PlanBaseData = PlanBaseData(), var placeArray : ArrayList<PlaceInfo> = ArrayList()) : Comparable<UserPlanData>{
    override fun compareTo(other: UserPlanData): Int {
        if(this.baseData.startDate.compareTo(other.baseData.startDate) < 0){
            return -1
        }else if(this.baseData.startDate.compareTo(other.baseData.startDate) > 0){
            return 1
        }else{
            return this.baseData.endDate.compareTo(other.baseData.endDate)
        }
    }
}

data class LikeFolder(var likeUserFolder: ArrayList<String> = ArrayList())
data class CommentsData(var userEmail: String = "", var contents: String = "", var date: String = "")
data class CommentsFolder(var commentsFolder: ArrayList<CommentsData> = ArrayList())
data class DiaryBaseData(var idx : Long = 0, var title: String="", var mainImage: String="", var userEmail: String="", var uploadDate: String="",
                         var startDate: String = "", var endDate: String = "", var color: String = "", var area: String="", var friendsList: FriendList = FriendList(),
                         var like: LikeFolder=LikeFolder(), var comments: CommentsFolder = CommentsFolder(), var lock:String="")//기본정보


data class DiaryData(var imagePathArray : ArrayList<String> = ArrayList(), var diaryTitle : String = "", var diaryContents : String = "")

data class DiaryInfo(var date: String="", var diaryInfo : DiaryData = DiaryData(), var placeInfo: PlaceInfo = PlaceInfo()) : Comparable<DiaryInfo>{
    override fun compareTo(other: DiaryInfo): Int {
        return this.date.compareTo(other.date)
    }
}

data class UserDiaryData(var baseData: DiaryBaseData = DiaryBaseData(), var diaryArray: ArrayList<DiaryInfo> = ArrayList()) : Comparable<UserDiaryData> {
    override fun compareTo(other: UserDiaryData): Int {
        if (this.baseData.startDate.compareTo(other.baseData.startDate) < 0) {
            return -1
        } else if (this.baseData.startDate.compareTo(other.baseData.startDate) > 0) {
            return 1
        } else {
            return this.baseData.endDate.compareTo(other.baseData.endDate)
        }
    }
}

data class BulletinData(var userDiaryData : UserDiaryData = UserDiaryData(), var userInfo : UserInfo = UserInfo()) : Comparable<BulletinData>{
    override fun compareTo(other: BulletinData): Int {
        return this.userDiaryData.baseData.uploadDate.compareTo(other.userDiaryData.baseData.uploadDate)*(-1)
    }
}
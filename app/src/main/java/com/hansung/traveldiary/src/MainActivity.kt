package com.hansung.traveldiary.src

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.src.home.HomeFragment
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMainBinding
import com.hansung.traveldiary.src.bulletin.BulletinFragment
import com.hansung.traveldiary.src.chat.ChatFragment
import com.hansung.traveldiary.src.home.HomeBulletinData
import com.hansung.traveldiary.src.profile.ProfileFragment
import com.hansung.traveldiary.src.travel.AddBook.AddTravelPlanActivity
import com.hansung.traveldiary.src.travel.TravelBaseFragment
import com.hansung.traveldiary.util.LoadingDialog
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

data class WeeklyWeatherData(var date: String, var icon: Drawable, var min: String, var max: String)

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    private lateinit var addNewPlanBookTask: ActivityResultLauncher<Intent>
    private lateinit var updatePlanBookTask: ActivityResultLauncher<Intent>

    private val TAG = "MainActivity"

    companion object {
        var firstStart = true
        var weatherId: String = "800"
        var weatherMain: String = "맑음"
        lateinit var weatherIcon: Drawable
        var tempText = "30°C"
        var maxTempText = "30°C"
        var minTempText = "30°C"
        var feelLikeTempText = "30°C"
        var humidityText = "66%"
        var windSpeedText = "2.57m/s"
        var cloudsText: String = "30%"
        var weeklyList = ArrayList<WeeklyWeatherData>()

        var myFriendList = FriendList()
        var userList = UserList() //유저 이메일 정보 리스트
        var userInfoList = ArrayList<UserInfo>() //유저 닉네임, 이미지 정보 리스트
        var idxList = IdxList() //전체 idx 리스트
        var myPlanIdxList = IdxList() //나의 plan idx 리스트
        var myDiaryIdxList = IdxList() //나의 diary idx 리스트
        var bulletinIdxList = IdxList()
        var userPlanArray = ArrayList<UserPlanData>() //나의 plan data 리스트
        var userDiaryArray = ArrayList<UserDiaryData>() //나의 diary data 리스트
        var bulletinDiaryArray = ArrayList<BulletinData>() //게시글에 들어가는 전체 diary 리스트

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var isSend = false

        lateinit var mLoadingDialog: LoadingDialog

        fun showLoadingDialog(context: Context) {
            mLoadingDialog = LoadingDialog(context)
            mLoadingDialog.show()
        }

        fun dismissLoadingDialog() {
            if (mLoadingDialog.isShowing) {
                mLoadingDialog.dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initList()
        initBulletinList()

        weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_sunny_white, null)!!
        user = Firebase.auth.currentUser
        db = Firebase.firestore
        getDBData()

        val pref = applicationContext.getSharedPreferences("login", 0)
        Log.d("MainActivity", pref.getString("login", "")!!)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.main_btm_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "home")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_bulletin -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, BulletinFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "bulletin")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_travel -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "travel")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_message -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ChatFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "message")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_profile -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ProfileFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "profile")
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })

        addNewPlanBookTask = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                showLoadingDialog(this)
                println("resultcode 들어옴")
                val idx = result.data?.getLongExtra("idx", 0)!!
                var addPlanBaseData: PlanBaseData
                var addPlanPlaceArray = ArrayList<PlaceInfo>()
                val addPlanRef =
                    db!!.collection("Plan").document(user!!.email.toString()).collection("PlanData")
                        .document(idx.toString())
                addPlanRef.get()
                    .addOnSuccessListener { result ->
                        addPlanBaseData = result.toObject<PlanBaseData>()!!

                        for (i in 0..getCalcDate(
                            addPlanBaseData.startDate,
                            addPlanBaseData.endDate
                        )) {
                            addPlanRef.collection("PlaceInfo")
                                .document(afterDate(addPlanBaseData.startDate, i)).get()
                                .addOnSuccessListener { result ->
                                    val placeData = result.toObject<PlaceInfo>()
                                    if (placeData != null)
                                        addPlanPlaceArray.add(placeData)
                                }
                        }
                        userPlanArray.add(UserPlanData(addPlanBaseData, addPlanPlaceArray))
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                        dismissLoadingDialog()
                    }
            }
        }

        updatePlanBookTask = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val index = result.data?.getIntExtra("index", 0)!!
                val idx = result.data?.getLongExtra("idx", 0)!!
                userPlanArray.removeAt(index)

                var updatePlanBaseData: PlanBaseData
                var updatePlanPlaceArray = ArrayList<PlaceInfo>()

                val updatePlanRef =
                    db!!.collection("Plan").document(user!!.email.toString()).collection("PlanData")
                        .document(idx.toString())
                updatePlanRef.get()
                    .addOnSuccessListener { result ->
                        updatePlanBaseData = result.toObject<PlanBaseData>()!!

                        for (i in 0..getCalcDate(
                            updatePlanBaseData.startDate,
                            updatePlanBaseData.endDate
                        )) {
                            updatePlanRef.collection("PlaceInfo")
                                .document(afterDate(updatePlanBaseData.startDate, i)).get()
                                .addOnSuccessListener { result ->
                                    val placeData = result.toObject<PlaceInfo>()
                                    if (placeData != null)
                                        updatePlanPlaceArray.add(placeData)
                                }
                        }
                        userPlanArray.add(UserPlanData(updatePlanBaseData, updatePlanPlaceArray))
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                        dismissLoadingDialog()
                    }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        firstStart = true
    }

    private fun initList() {
        myFriendList.friendFolder.clear()
        userList.emailFolder.clear()
        userInfoList.clear()
        idxList.idxFolder.clear()
        myPlanIdxList.idxFolder.clear()
        myDiaryIdxList.idxFolder.clear()
        userPlanArray.clear()
        userDiaryArray.clear()
        bulletinDiaryArray.clear()
    }

    fun makePlanBook() {
        addNewPlanBookTask.launch(Intent(this@MainActivity, AddTravelPlanActivity::class.java))
    }

    fun updatePlanBook(index: Int, modify: Boolean) {
        val intent = Intent(this@MainActivity, AddTravelPlanActivity::class.java)
        intent.putExtra("index", index)
        intent.putExtra("modify", true)
        updatePlanBookTask.launch(intent)
    }

    fun removePlanBook(index: Int) {
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val idx = userPlanArray[index].baseData.idx

        //userPlanArray에서 삭제
        userPlanArray.removeAt(index)
        idxList.idxFolder.remove(idx)

        //IdxDatabase에서 삭제
        var totalIdxList = IdxList()
        val totalIdxRef = db.collection("IdxDatabase").document("IdxData")
        totalIdxRef.get()
            .addOnSuccessListener { result ->
                totalIdxList = result.toObject<IdxList>()!!
                totalIdxList.idxFolder.remove(idx)
                totalIdxRef.set(totalIdxList)
                idxList = totalIdxList
            }

        //Plan - 유저 email에서 idxFolder / PlanData 안 document 삭제.
        val userRef = db.collection("Plan").document(user!!.email.toString())
        myPlanIdxList.idxFolder.remove(idx)
        userRef.set(myPlanIdxList)

        userRef.collection("PlanData").document(idx.toString())
            .delete()
            .addOnSuccessListener {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, TravelBaseFragment())
                    .commitAllowingStateLoss()
            }
    }

    fun removeDiary(index: Int) {
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val idx = userDiaryArray[index].baseData.idx

        userDiaryArray.removeAt(index)
        idxList.idxFolder.remove(idx)

        var totalIdxList = IdxList()
        val totalIdxRef = db.collection("IdxDatabase").document("IdxData")
        totalIdxRef.get()
            .addOnSuccessListener { result ->
                totalIdxList = result.toObject<IdxList>()!!
                totalIdxList.idxFolder.remove(idx)
                totalIdxRef.set(totalIdxList)
                idxList = totalIdxList
            }

        //Diary - 유저 email에서 idxFolder / DiaryData 안 document 삭제.
        val userRef = db.collection("Diary").document(user!!.email.toString())
        myDiaryIdxList.idxFolder.remove(idx)
        userRef.set(myDiaryIdxList)

        userRef.collection("DiaryData").document(idx.toString())
            .delete()
            .addOnSuccessListener {
                println("삭제성공")
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.main_frm, TravelBaseFragment())
//                    .commitAllowingStateLoss()
            }
    }

    private fun getDBData() {
        showLoadingDialog(this)
        getMyFriendList()
        getUserList()
        getTotalIdxList()
        getMyPlanData()
        getMyDiaryData()
        getBulletinData()
        dismissLoadingDialog() //임시
    }

    private fun getMyFriendList() {
        db!!.collection("UserInfo").document(user!!.email.toString())
            .get().addOnSuccessListener { result ->
                val data = result.toObject<UserInfo>()
                if (data != null) {
                    myFriendList = data.friendList
                } else {
                    Toast.makeText(this, "UserInfo가 없대", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun getUserList() {
        db!!.collection("UserData").document("UserEmail")
            .get()
            .addOnSuccessListener { result ->
                userList = result.toObject<UserList>()!!
                getUserInfoList()
//                getAllDiaryData()
            }
    }

    private fun getUserInfoList() {
        for (email in userList.emailFolder) {
            db!!.collection("UserInfo").document(email)
                .get()
                .addOnSuccessListener { result ->
                    val data = result.toObject<UserInfo>()!!
                    userInfoList.add(data)
                }
        }
    }

    private fun getTotalIdxList() {
        db!!.collection("IdxDatabase").document("IdxData")
            .get()
            .addOnSuccessListener { result ->
                idxList = result.toObject<IdxList>()!!
            }
    }

    /* fun getAllDiaryData() {
         for (email in userList.emailFolder) {
             var userInfo = UserInfo()
             db!!.collection("UserInfo").document(email)
                 .get().addOnSuccessListener { result ->
                     userInfo = result.toObject<UserInfo>()!!
                 }

             var diaryIdxList = IdxList()
             println("getDiary email:${email}")
             val diaryIdxRef = db!!.collection("Diary").document(email)
             diaryIdxRef.get()
                 .addOnSuccessListener { result ->
                     val idxData = result.toObject<IdxList>()
                     if (idxData != null) {
                         diaryIdxList = idxData
                         if (user!!.email.toString() == email)
                             myDiaryIdxList = diaryIdxList
                         for (idx in diaryIdxList.idxFolder) {
                             var diaryBaseData = DiaryBaseData()
                             println("${email} idx: ${idx}")
                             val baseRef =
                                 diaryIdxRef.collection("DiaryData").document(idx.toString())
                             baseRef.get().addOnSuccessListener { baseResult ->
                                 val baseData = baseResult.toObject<DiaryBaseData>()
                                 if (baseData != null) {
                                     diaryBaseData = baseData
                                     var diaryArray = ArrayList<DiaryInfo>()
                                     val calcDate =
                                         getCalcDate(diaryBaseData.startDate, diaryBaseData.endDate)
                                     for (i in 0..calcDate) {
                                         val date = afterDate(diaryBaseData.startDate, i)
                                         baseRef.collection("DayList").document(date)
                                             .get().addOnSuccessListener { dayResult ->
                                                 val diaryData = dayResult.toObject<DiaryInfo>()
                                                 if (diaryData != null) {
                                                     diaryArray.add(diaryData)
                                                     if (diaryArray.size == calcDate + 1) {
                                                         diaryArray.sortBy { it.date }
                                                         println("---------------------Bulletin체크  idx:${idx}-----------------")
                                                         for (j in 0 until diaryArray.size) {
                                                             println(diaryArray[j].date + " / " + diaryArray[j].diaryInfo.diaryTitle + " / " + diaryArray[j].diaryInfo.diaryContents)
                                                         }

                                                         if (email == user!!.email.toString()) {
                                                             println("---------------------MyDiary체크-----------------")
                                                             for (j in 0 until diaryArray.size) {
                                                                 println(diaryArray[j].date + " / " + diaryArray[j].diaryInfo.diaryTitle + " / " + diaryArray[j].diaryInfo.diaryContents)
                                                             }
                                                             userDiaryArray.add(
                                                                 UserDiaryData(
                                                                     diaryBaseData,
                                                                     diaryArray
                                                                 )
                                                             )
                                                         }
                                                         bulletinDiaryArray.add(
                                                             BulletinData(
                                                                 UserDiaryData(
                                                                     diaryBaseData,
                                                                     diaryArray
                                                                 ), userInfo
                                                             )
                                                         )
                                                         println("MyBulletin SIZE: ${bulletinDiaryArray.size}")
                                                         dismissLoadingDialog()
                                                     }
                                                 }

                                             }
                                     }
                                 }
                             }
                         }
                     } else {
                         println("${email} idx null입니다")
                         dismissLoadingDialog()
                     }
                 }

         }
     }*/

    fun getBulletinData() {
        val bulletinRef =
            db!!.collection("Bulletin").document("BulletinData")

        bulletinRef.get().addOnSuccessListener { result ->
            println("getBulletin성공")
            val idxData = result.toObject<IdxList>()
            if (idxData != null) {
                bulletinIdxList = idxData
                for (idx in bulletinIdxList.idxFolder) {
                    var diaryBaseData = DiaryBaseData()
                    bulletinRef.collection(idx.toString()).document("idxUserData")
                        .get().addOnSuccessListener { userResult ->
                            val userInfo = userResult.toObject<UserInfo>()!!
                            Log.d("BulletinGet", userInfo.email+"/"+idx)
                            val baseRef = db!!.collection("Diary").document(userInfo.email)
                                .collection("DiaryData").document(idx.toString())
                            baseRef.get().addOnSuccessListener { baseResult ->
                                val baseData = baseResult.toObject<DiaryBaseData>()
                                if (baseData != null && baseData.lock == "false") {
                                    Log.d("Bulletin Get", "${baseData.idx} / ${baseData.userEmail}")
                                    diaryBaseData = baseData
                                    var diaryArray = ArrayList<DiaryInfo>()
                                    val calcDate = getCalcDate(diaryBaseData.startDate, diaryBaseData.endDate)
                                    for (i in 0..calcDate) {
                                        val date = afterDate(diaryBaseData.startDate, i)
                                        baseRef.collection("DayList").document(date)
                                            .get().addOnSuccessListener { dayResult ->
                                                val diaryData = dayResult.toObject<DiaryInfo>()
                                                if (diaryData != null) {
                                                    diaryArray.add(diaryData)
                                                    if (diaryArray.size == calcDate + 1) {
                                                        diaryArray.sortBy { it.date }
                                                        println("---------------------Bulletin체크  idx:${idx}-----------------")
                                                        for (j in 0 until diaryArray.size) {
                                                            println(diaryArray[j].date + " / " + diaryArray[j].diaryInfo.diaryTitle + " / " + diaryArray[j].diaryInfo.diaryContents)
                                                        }

                                                        bulletinDiaryArray.add(
                                                            BulletinData(
                                                                UserDiaryData(
                                                                    diaryBaseData,
                                                                    diaryArray
                                                                ), userInfo
                                                            )
                                                        )
                                                        println("MyBulletin SIZE: ${bulletinDiaryArray.size}")
                                                        dismissLoadingDialog()
                                                    }
                                                }
                                            }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    fun getMyDiaryData() {
        val myDiaryIdxRef =
            db!!.collection("Diary").document(user!!.email.toString())
        myDiaryIdxRef.get()
            .addOnSuccessListener { result ->
                val idxData = result.toObject<IdxList>()
                if (idxData != null) {
                    myDiaryIdxList = idxData

                    for (myIdx in myDiaryIdxList.idxFolder) {
                        var diaryBaseData = DiaryBaseData()
                        var diaryArray = ArrayList<DiaryInfo>()

                        val myDiaryRef =
                            myDiaryIdxRef.collection("DiaryData").document(myIdx.toString())
                        myDiaryRef.get().addOnSuccessListener { baseResult ->
                            val diaryData = baseResult.toObject<DiaryBaseData>()
                            if (diaryData != null) {
                                diaryBaseData = diaryData
                                Log.d("수정체크", diaryBaseData.startDate)
                                val calcDate =
                                    getCalcDate(diaryBaseData.startDate, diaryBaseData.endDate)
                                for (i in 0..calcDate) {
                                    myDiaryRef.collection("DayList")
                                        .document(afterDate(diaryBaseData.startDate, i))
                                        .get().addOnSuccessListener { infoResult ->
                                            val diaryInfoData = infoResult.toObject<DiaryInfo>()
                                            if (diaryInfoData != null) {
                                                Log.d("수정체크", diaryInfoData.date)
                                                diaryArray.add(diaryInfoData)

                                                if (diaryArray.size == calcDate + 1) {
                                                    diaryArray.sortBy { it.date }
                                                    println("---------------------MyDiary체크-----------------")
                                                    for (j in 0 until diaryArray.size) {
                                                        println(diaryArray[j].date + " / " + diaryArray[j].diaryInfo.diaryTitle + " / " + diaryArray[j].diaryInfo.diaryContents)
                                                    }
                                                    userDiaryArray.add(
                                                        UserDiaryData(
                                                            diaryBaseData,
                                                            diaryArray
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }
    }

    fun getMyPlanData() {
        val myPlanIdxRef =
            db!!.collection("Plan").document(user!!.email.toString())

        myPlanIdxRef.get()
            .addOnSuccessListener { result ->
                println("getPlan성공")
                val idxData = result.toObject<IdxList>()
                if (idxData != null) {
                    myPlanIdxList = idxData
                    println("플랜 idx: ${myPlanIdxList.idxFolder.size}")
                    for (myIdx in myPlanIdxList.idxFolder) {
                        var planBaseData = PlanBaseData()
                        var placeArray = ArrayList<PlaceInfo>()
                        val myPlanRef = myPlanIdxRef.collection("PlanData")
                            .document(myIdx.toString())
                        myPlanRef.get().addOnSuccessListener { result ->
                            val planData = result.toObject<PlanBaseData>()
                            if (planData != null) {
                                planBaseData = planData

                                for (i in 0..getCalcDate(
                                    planBaseData.startDate,
                                    planBaseData.endDate
                                )) {
                                    myPlanRef.collection("PlaceInfo")
                                        .document(afterDate(planBaseData.startDate, i)).get()
                                        .addOnSuccessListener { result ->
                                            val placeData = result.toObject<PlaceInfo>()
                                            if (placeData != null)
                                                placeArray.add(placeData)
                                        }
                                }

                                userPlanArray.add(UserPlanData(planBaseData, placeArray))
                            }
                        }
                    }
                } else {
                    println("plan Idx null 아님")
                }
            }
    }

    fun getCalcDate(startDate: String, endDate: String): Int {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDateFormat = simpleDateFormat.parse("${startDate} 00:00:00")!!
        val endDateFormat = simpleDateFormat.parse("${endDate} 00:00:00")!!
        val calcDate =
            ((endDateFormat.time - startDateFormat.time) / (60 * 60 * 24 * 1000)).toInt()
        return calcDate
    }

    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }

    fun initBulletinList(){
        var imagepath = ArrayList<String>()
        imagepath.add("data:image/jpeg;base64,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")
        var diaryInfo = ArrayList<DiaryInfo>()
        diaryInfo.add(
            DiaryInfo("", DiaryData(
                imagepath,
                "서울 당일치기 여행",
                "서울 데이트코스 덕수궁 돌담길 야경 맛집"
            ))
        )
        bulletinDiaryArray.add(
            BulletinData(
                UserDiaryData(DiaryBaseData(0, "서울 당일치기 여행","data:image/jpeg;base64,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", "", "", "", "", "", "", FriendList(), LikeFolder(), CommentsFolder(),"")), UserInfo("", "딸기", "", FriendList())
            )
        )

        bulletinDiaryArray.add(
            BulletinData(
                UserDiaryData(DiaryBaseData(0, "잊지못할 부산 1박 2일","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4jAKyX8tbXiM7Uf7bj7D31MAMUeznCFcyWQ&usqp=CAU", "", "", "", "", "", "", FriendList(), LikeFolder(), CommentsFolder(),"")), UserInfo("", "아보카도", "", FriendList())
            )
        )

        bulletinDiaryArray.add(
            BulletinData(
                UserDiaryData(DiaryBaseData(0, "제주도 핫한 여행지 5곳","https://api.cdn.visitjeju.net/photomng/imgpath/201911/28/1b150513-5d25-4212-826a-c70c6fd0ac78.jpg", "", "", "", "", "", "", FriendList(), LikeFolder(), CommentsFolder(),"")), UserInfo("", "정근", "", FriendList())
            )
        )

//        bulletinDiaryArray.add(
//            BulletinData(
//                UserDiaryData(DiaryBaseData(0, "국내 여행 : 여수, 순천 여행","https://scbay.suncheon.go.kr/wetland/images/cont/photo/700/scbay-0064.jpg", "", "", "", "", "", "", FriendList(), LikeFolder(), CommentsFolder(),"")), UserInfo("", "사과", "", FriendList())
//            )
//        )

        bulletinDiaryArray.add(
            BulletinData(
                UserDiaryData(DiaryBaseData(0, "아름다운 풍경 가득한 강릉 여행 코스","https://www.gn.go.kr/tour/images/tour/sub03/sub030210_img01.jpg", "", "", "", "", "", "", FriendList(), LikeFolder(), CommentsFolder(),"")), UserInfo("", "귤", "", FriendList())
            )
        )



//        bulletinDiaryArray.add(
//            BulletinData(
//                ResourcesCompat.getDrawable(
//                    resources,
//                    R.drawable.ig_home_busan,
//                    null
//                )!!, "잊지못할 부산 1박 2일", "부산 해운데 더베이 101, 주변 여행 코스"
//            )
//        )
//        bulletinDiaryArray.add(
//            BulletinData(
//                ResourcesCompat.getDrawable(
//                    resources,
//                    R.drawable.ig_home_gangneung,
//                    null
//                )!!, "제주도 핫한 여행지 5곳", "천지연 폭포, 휴애리자연생활공원, 노형수퍼마켙, 제주 블라썸, 외돌개 황우지해안을 따라"
//            )
//        )
//        bulletinDiaryArray.add(
//            BulletinData(
//                ResourcesCompat.getDrawable(
//                    resources,
//                    R.drawable.ig_home_seoul_nearby,
//                    null
//                )!!, "국내 여행 : 여수, 순천 여행", "아르떼 뮤지엄/구백식당/향일암/베네치아 호텔/이순신 광장 등 가볼만한 곳 추천"
//            )
//        )
//        bulletinDiaryArray.add(
//            BulletinData(
//                ResourcesCompat.getDrawable(
//                    resources,
//                    R.drawable.ig_home_daejeon,
//                    null
//                )!!, "아름다운 풍경 가득한 강릉 여행 코스", "정동진 레일바이크로 예쁜 풍경 구경하며 시원한 바닷바람 즐기기"
//            )
//        )
    }

}



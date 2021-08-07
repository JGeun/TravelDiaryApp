package com.hansung.traveldiary.src

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private lateinit var removePlanBookTask: ActivityResultLauncher<Intent>

    private val TAG = "MainActivity"
    private var userList = UserList()

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

        var userList = UserList() //유저 이메일 정보 리스트
        var idxList = IdxList() //전체 idx 리스트
        var myPlanIdxList = IdxList() //나의 plan idx 리스트
        var myDiaryIdxList = IdxList() //나의 diary idx 리스트
        var userPlanArray = ArrayList<UserPlanData>() //나의 plan data 리스트
        var userDiaryArray = ArrayList<DiaryTotalData>() //나의 diary data 리스트

        var diaryTitleList = TitleList()
        var planTitleList = TitleList()
        val planBookList = ArrayList<PlanBookData>()
        val myDiaryList = ArrayList<DiaryBulletinData>()
        val allDiaryList = ArrayList<DiaryBulletinData>()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

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

                        for (i in 0..getCalcDate(addPlanBaseData.startDate, addPlanBaseData.endDate)) {
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

                val updatePlanRef = db!!.collection("Plan").document(user!!.email.toString()).collection("PlanData")
                    .document(idx.toString())
                updatePlanRef.get()
                    .addOnSuccessListener { result ->
                        updatePlanBaseData = result.toObject<PlanBaseData>()!!

                        for (i in 0..getCalcDate(updatePlanBaseData.startDate, updatePlanBaseData.endDate)) {
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
        println("MainStart")
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

    fun removePlanBook(index: Int){
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val idx = userPlanArray[index].planBaseData.idx

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

    fun getUserList() {
        db!!.collection("UserData").document("UserEmail")
            .get()
            .addOnSuccessListener { result ->
                userList = result.toObject<UserList>()!!
            }
    }

    fun getTotalIdxList() {
        db!!.collection("IdxDatabase").document("IdxData")
            .get()
            .addOnSuccessListener { result ->
                idxList = result.toObject<IdxList>()!!
            }
    }

    fun getDBData() {
        showLoadingDialog(this)
        getUserList()
        getTotalIdxList()
        getMyPlanData()
        getMyDiaryData()
//        getAllDiaryData()
    }

    fun getMyDiaryData(){
        val myDiaryIdxRef = db!!.collection("Diary").document(user!!.email.toString())
        myDiaryIdxRef.get()
            .addOnSuccessListener { result ->
                val idxData = result.toObject<IdxList>()
                if (idxData != null) {
                    myDiaryIdxList = idxData

                    for(myIdx in myDiaryIdxList.idxFolder){
                        var diaryBaseData = DiaryBaseData()
                        var diaryTotalData = UserDiaryData()

                        val myDiaryRef = myDiaryIdxRef.collection("DiaryData").document(myIdx.toString())
                        myDiaryRef.get().addOnSuccessListener { result->
                                val diaryData = result.toObject<DiaryBaseData>()
                                if(diaryData != null){
                                    diaryBaseData = diaryData

                                    for(i in 0..getCalcDate(diaryBaseData.startDate, diaryBaseData.endDate)){
                                        myDiaryRef.collection("DayList").document(afterDate(diaryBaseData.startDate, i))
                                            .get().addOnSuccessListener { result->
                                                val totalData = result.toObject<UserDiaryData>()
                                                if(totalData != null){
                                                    diaryTotalData = totalData
                                                }
                                            }
                                    }
                                    userDiaryArray.add(DiaryTotalData(diaryBaseData, diaryTotalData))
                                }
                            }

                    }
                }
            }
    }

    fun getMyPlanData() {
        val myPlanIdxRef = db!!.collection("Plan").document(user!!.email.toString())

        myPlanIdxRef.get()
            .addOnSuccessListener { result ->
                val idxData = result.toObject<IdxList>()
                if (idxData != null) {
                    myPlanIdxList = idxData

                    for (myIdx in myPlanIdxList.idxFolder) {
                        var planBaseData = PlanBaseData()
                        var placeArray = ArrayList<PlaceInfo>()
                        val myPlanRef = myPlanIdxRef.collection("PlanData").document(myIdx.toString())
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
                            dismissLoadingDialog()
                        }
                    }
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

    /*fun getPlanData() {
        val userDocRef = db!!.collection("User").document("UserData")
        //getTitle
        userDocRef.collection(user!!.email.toString()).document("Plan")
            .get()
            .addOnSuccessListener { result ->
                val data = result.data?.get("titleFolder")
                if (data != null) {
                    planTitleList.titleFolder = data as ArrayList<String>
                    println("size: ${planTitleList.titleFolder.size}")
                    println("content: ${planTitleList.titleFolder[0]}")
                }
                //get Data about Title
                getPlanAllData()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }*/

    /*fun getPlanAllData() {
        for (title in planTitleList.titleFolder) {
            addPlan2PlanBookList(title)
        }
    }*/

    /*fun addPlan2PlanBookList(title: String, check: String = "default") {
        val userDocRef = db!!.collection("User").document("UserData")
        val planDocRef = userDocRef.collection(user!!.email.toString()).document("Plan")
        var planBaseData: PlanBaseData2? = null
        var placeInfoFolder: PlaceInfoFolder2? = null
        planDocRef.collection(title).document("BaseData")
            .get()
            .addOnSuccessListener { result ->
                planBaseData = result.toObject<PlanBaseData2>()
                if (planBaseData != null && placeInfoFolder != null) {
                    planBookList.add(
                        PlanBookData(
                            title,
                            PlanData(planBaseData!!, placeInfoFolder!!)
                        )
                    )
                    if (check == "add") {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        planDocRef.collection(title).document("PlaceInfo")
            .get()
            .addOnSuccessListener { result ->
                placeInfoFolder = result.toObject<PlaceInfoFolder2>()
                if (planBaseData != null && placeInfoFolder != null) {
                    planBookList.add(
                        PlanBookData(
                            title,
                            PlanData(planBaseData!!, placeInfoFolder!!)
                        )
                    )
                    if (check == "add") {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }*/


    fun getAllDiaryData() {
        //getTitle
        println("getAllDiaryData")
        val myEmail = user!!.email.toString()
        val userDocRef = db!!.collection("User").document("UserData")
        userDocRef.get()
            .addOnSuccessListener { result ->
                println("emailFolder")
                val data = result.data?.get("emailFolder")
                if (data != null) {
                    userList.emailFolder = data as ArrayList<String>

                    for (email in userList.emailFolder) {
                        userDocRef.collection(email).document("Diary")
                            .get()
                            .addOnSuccessListener { result ->
                                println("titleFolder")
                                val data = result.data?.get("titleFolder")
                                if (data != null) {
                                    diaryTitleList.titleFolder = data as ArrayList<String>
                                    for (title in diaryTitleList.titleFolder) {
                                        if (myEmail == email)
                                            getUserDiaryData(email, title, true)
                                        else
                                            getUserDiaryData(email, title, false)
                                    }
                                }
                                dismissLoadingDialog()
                            }
                    }
                }

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


    fun getUserDiaryData(email: String, title: String, isMyEmail: Boolean) {
        println("getUserDiaryData")
        val userDocRef = db!!.collection("User").document("UserData")
        val diaryDocRef = userDocRef.collection(email).document("Diary")

        var planBaseData: PlanBaseData2? = null
        var placeInfoFolder: PlaceInfoFolder2? = null
        var diaryInfoFolder: DiaryInfo2? = null
        var diaryBaseData: DiaryBaseData? = null

        diaryDocRef.collection(title).document("PlanBaseData")
            .get()
            .addOnSuccessListener { result ->
                planBaseData = result.toObject<PlanBaseData2>()
                if (planBaseData != null && placeInfoFolder != null && diaryInfoFolder != null && diaryBaseData != null) {
                    val diaryBulletinData = DiaryBulletinData(
                        title,
                        DiaryTotalData2(
                            planBaseData!!,
                            diaryBaseData!!,
                            placeInfoFolder!!,
                            diaryInfoFolder!!
                        )
                    )
                    allDiaryList.add(diaryBulletinData)
                    if (isMyEmail) {
                        myDiaryList.add(diaryBulletinData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        diaryDocRef.collection(title).document("PlanPlaceInfo")
            .get()
            .addOnSuccessListener { result ->
                placeInfoFolder = result.toObject<PlaceInfoFolder2>()
                if (planBaseData != null && placeInfoFolder != null && diaryInfoFolder != null && diaryBaseData != null) {
                    val diaryBulletinData = DiaryBulletinData(
                        title,
                        DiaryTotalData2(
                            planBaseData!!,
                            diaryBaseData!!,
                            placeInfoFolder!!,
                            diaryInfoFolder!!
                        )
                    )
                    allDiaryList.add(diaryBulletinData)
                    if (isMyEmail) {
                        myDiaryList.add(diaryBulletinData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        diaryDocRef.collection(title).document("DiaryBaseData")
            .get()
            .addOnSuccessListener { result ->
                diaryBaseData = result.toObject<DiaryBaseData>()
                if (planBaseData != null && placeInfoFolder != null && diaryInfoFolder != null && diaryBaseData != null) {
                    val diaryBulletinData = DiaryBulletinData(
                        title,
                        DiaryTotalData2(
                            planBaseData!!,
                            diaryBaseData!!,
                            placeInfoFolder!!,
                            diaryInfoFolder!!
                        )
                    )
                    allDiaryList.add(diaryBulletinData)
                    if (isMyEmail) {
                        myDiaryList.add(diaryBulletinData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        diaryDocRef.collection(title).document("DiaryData")
            .get()
            .addOnSuccessListener { result ->
                diaryInfoFolder = result.toObject<DiaryInfo2>()
                if (planBaseData != null && placeInfoFolder != null && diaryInfoFolder != null && diaryBaseData != null) {
                    val diaryBulletinData = DiaryBulletinData(
                        title,
                        DiaryTotalData2(
                            planBaseData!!,
                            diaryBaseData!!,
                            placeInfoFolder!!,
                            diaryInfoFolder!!
                        )
                    )
                    allDiaryList.add(diaryBulletinData)
                    if (isMyEmail) {
                        myDiaryList.add(diaryBulletinData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

}


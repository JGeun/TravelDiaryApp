package com.hansung.traveldiary.src.diary

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hansung.traveldiary.databinding.ActivitySendTravelPlanBinding
import com.hansung.traveldiary.src.*
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SendTravelPlanActivity : AppCompatActivity() {
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private var titleList = TitleList()
    private val binding by lazy {
        ActivitySendTravelPlanBinding.inflate(layoutInflater)
    }

    private var index = 0

    private lateinit var getResultImage: ActivityResultLauncher<Intent>
    private var getImagePath: String = ""
    private var mainImagePath: String = ""
    private var planTitle = ""

    private var placeInfo: PlaceInfo = PlaceInfo()

    private val TAG = "SendTravelPlanActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance().currentUser
        db = Firebase.firestore
        index = intent.getIntExtra("index", 0)

//        getTitleList()
//        getDataAboutPlanTitle()

        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                getImagePath = result.data?.getStringExtra("imagePath")!!
                Glide.with(this).load(getImagePath).circleCrop().into(binding.stpMainImage)

                val op = BitmapFactory.Options()
                var bitmap: Bitmap? = null
                bitmap = BitmapFactory.decodeFile(getImagePath, op)
                uploadFirebase2Image(bitmap)
            }
        }

        binding.stpImageView.setOnClickListener {
            getResultImage.launch(
                Intent(
                    this@SendTravelPlanActivity,
                    SelectPictureActivity::class.java
                )
            )
        }

        binding.sendPlanBtn.setOnClickListener {
//            var diaryTitle = binding.editTravelTitle.text.toString()
//
//            val idx = MainActivity.userPlanArray[index].planBaseData.idx
//            MainActivity.myDiaryIdxList.idxFolder.add(idx)
//            val idxRef =db!!.collection("Diary").document(user!!.email.toString())
//            idxRef.set(MainActivity.myDiaryIdxList)
//
//            val now = System.currentTimeMillis();
//            val mDate = Date(now)
//            val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            val getTime = simpleDate.format(mDate)
//
//            val startDate = MainActivity.userPlanArray[index].planBaseData.startDate
//            val endDate = MainActivity.userPlanArray[index].planBaseData.endDate
//            idxRef.collection("DiaryData").document(idx.toString()).set(
//                DiaryBaseData(
//                    idx,
//                    diaryTitle,
//                    mainImagePath,
//                    user!!.email.toString(),
//                    getTime,
//                    startDate,
//                    endDate,
//                    MainActivity.userPlanArray[index].planBaseData.color,
//                    MainActivity.userPlanArray[index].planBaseData.area,
//                    MainActivity.userPlanArray[index].planBaseData.peopleCount,
//                    0,
//                    0
//                )
//            )
//
//            val diaryRef = idxRef.collection("DiaryData").document(idx.toString()).collection("DayList")
//            val calcDate = getCalcDate(startDate, endDate)
//            for (i in 0..calcDate) {
//                val dayRef = diaryRef.document(afterDate(startDate, i))
//                dayRef.set(
//                   UserDiaryData(DiaryInfo(ArrayList()), MainActivity.userPlanArray[index].placeArray[i])
//                ).addOnSuccessListener {
//                    showCustomToast("끝")
//                    finish()
//                }
//            }
            finish()

        }

        binding.outblock.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

        binding.mainblock.setOnClickListener {
        }
    }

    fun uploadFirebase2Image(bitmap: Bitmap) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageStorageRef = storageRef.child("/diary/${user!!.email}/${MainActivity.userPlanArray[index].planBaseData.idx}/mainImage.png")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = imageStorageRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageStorageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("체크", downloadUri.toString())
                mainImagePath = downloadUri.toString()
                showCustomToast("메인 이미지가 변경되었습니다.")
            }
        }
    }

    private fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }

    fun getCalcDate(startDate: String, endDate: String): Int {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDateFormat = simpleDateFormat.parse("${startDate} 00:00:00")!!
        val endDateFormat = simpleDateFormat.parse("${endDate} 00:00:00")!!
        val calcDate =
            ((endDateFormat.time - startDateFormat.time) / (60 * 60 * 24 * 1000)).toInt()
        return calcDate
    }

    fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
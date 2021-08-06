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
import com.google.firebase.firestore.ktx.toObject
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

    private lateinit var getResultImage: ActivityResultLauncher<Intent>
    private lateinit var imagePath: String
    private var planTitle = ""

    private var planBaseData: PlanBaseData2 = PlanBaseData2()
    private var placeInfoFolder: PlaceInfoFolder2 = PlaceInfoFolder2()

    private val TAG = "SendTravelPlanActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance().currentUser
        db = Firebase.firestore

        planTitle = intent.getStringExtra("title").toString()

        getTitleList()
        getDataAboutPlanTitle()

        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                imagePath = result.data?.getStringExtra("imagePath")!!
                Glide.with(this).load(imagePath).circleCrop().into(binding.stpMainImage)

                val op = BitmapFactory.Options()
                var bitmap: Bitmap? = null
                bitmap = BitmapFactory.decodeFile(imagePath, op)
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
            var diaryTitle = binding.editTravelTitle.text.toString()
            var hashTag = binding.editHashtag.text.toString()
//            var image = binding.stpMainImage.drawable
            var imagePath = ""

            val userDocRef = db!!.collection("User").document("UserData")
            val docDiaryRef = userDocRef.collection(user!!.email.toString()).document("Diary")
            titleList.titleFolder.add(planTitle)
            docDiaryRef.set(titleList)

            docDiaryRef.collection(planTitle).document("PlanBaseData").set(planBaseData)
            docDiaryRef.collection(planTitle).document("PlanPlaceInfo").set(placeInfoFolder)

            val diaryDayList = ArrayList<DiaryDayInfo>()
            for (i in 0 until placeInfoFolder.dayPlaceList.size)
                diaryDayList.add(DiaryDayInfo(placeInfoFolder.dayPlaceList[i].date, DiaryInfo()))
            docDiaryRef.collection(planTitle).document("DiaryData").set(
                DiaryInfoFolder(diaryDayList)
            )

            val now = System.currentTimeMillis();
            val mDate = Date(now)
            val simpleDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            val getTime = simpleDate.format(mDate)

            Firebase.storage.reference.child("profileImage/" + user!!.email + "/profileImage.png")
                .downloadUrl.addOnCompleteListener { task ->
                    val downloadUri = task.result
                    imagePath = downloadUri.toString()

                    docDiaryRef.collection(planTitle).document("DiaryBaseData").set(
                        DiaryBaseData(
                            diaryTitle,
                            imagePath,
                            user!!.email.toString(),
                            user!!.displayName.toString(),
                            getTime,
                            "",
                            0,
                            0
                        )
                    ).addOnSuccessListener {
                        println("이미지 넣기 끝!")
                        showCustomToast("끝")
                        finish()
                    }
                }.addOnFailureListener {
                    showCustomToast("실패")
                }
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
        val imageStorageRef = storageRef.child("/diary/${user!!.email}/${planTitle}/mainImage.png")
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
                showCustomToast("메인 이미지가 변경되었습니다.")
            } else {

            }
        }
    }

    fun getTitleList() {
        val userDocRef = db!!.collection("User").document("UserData")
        userDocRef.collection(user!!.email.toString()).document("Diary")
            .get()
            .addOnSuccessListener { result ->
                val data = result.data?.get("titleFolder")
                if (data != null) {
                    titleList.titleFolder = data as ArrayList<String>
                    println("size: ${titleList.titleFolder.size}")
                    println("content: ${titleList.titleFolder[0]}")
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
                finish()
            }
    }

    fun getDataAboutPlanTitle() {
        val userDocRef = db!!.collection("User").document("UserData")
        val planDocRef =
            userDocRef.collection(user!!.email.toString()).document("Plan").collection(planTitle)
        planDocRef.document("BaseData")
            .get()
            .addOnSuccessListener { result ->
                planBaseData = result.toObject<PlanBaseData2>()!!
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
                finish()
            }

        planDocRef.document("PlaceInfo")
            .get()
            .addOnSuccessListener { result ->
                placeInfoFolder = result.toObject<PlaceInfoFolder2>()!!
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
                finish()
            }
    }

    fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
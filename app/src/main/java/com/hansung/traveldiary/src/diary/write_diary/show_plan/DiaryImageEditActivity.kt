package com.hansung.traveldiary.src.diary.write_diary.show_plan

import android.R.attr.src
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hansung.traveldiary.databinding.ActivityDiaryImageEditBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.diary.write_diary.CountViewModel
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity
import com.hansung.traveldiary.util.LoadingDialog
import com.hansung.traveldiary.util.StatusBarUtil
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class DiaryImageEditActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDiaryImageEditBinding.inflate(layoutInflater)
    }

    private lateinit var getResultImage: ActivityResultLauncher<Intent>
    private lateinit var imagePath: String
    private val countViewModel: CountViewModel by viewModels()
    private val imagePathList = ArrayList<String>()
    lateinit var mLoadingDialog: LoadingDialog

    private var index = 0
    private var day = 0
    private var title = ""
    private var user: FirebaseUser? = null

    private var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        user = Firebase.auth.currentUser
        db = Firebase.firestore
        index = intent.getIntExtra("index", 0)
        day = intent.getIntExtra("day", 0)
        val path = MainActivity.userDiaryArray[index].diaryArray[day]
        if (path.diaryInfo.imagePathArray.size != 0) {
            val size = path.diaryInfo.imagePathArray.size

            if (MainActivity.userDiaryArray[index].diaryArray[day].diaryInfo.imagePathArray.size != 0) {
                val size =
                    MainActivity.userDiaryArray[index].diaryArray[day].diaryInfo.imagePathArray.size
                countViewModel.setCount(size)
                for (i in 0 until size) {
                    imagePathList.add(MainActivity.userDiaryArray[index].diaryArray[day].diaryInfo.imagePathArray[i])
                }
            }

            binding.editImageRv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = EditImageAdapter(countViewModel, imagePathList)
                setHasFixedSize(true)
            }

            getResultImage = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    Log.d("갤러리", "OK")
                    imagePath = result.data?.getStringExtra("imagePath")!!
                    imagePathList.add(imagePath)
                    countViewModel.countUp()
                    binding.editImageRv.adapter?.notifyDataSetChanged()
                }
            }

            binding.addImageBtn.setOnClickListener {
                if (countViewModel.imageCount.value!! >= 5) {
                    //showCustomToast("사진은 최대 5개까지 넣으실 수 있어요")
                } else {
                    getResultImage.launch(Intent(this, SelectPictureActivity::class.java))
                }
            }

            binding.tvChecked.setOnClickListener {
                //이미지 메인 데이터에 넣고
                if (imagePathList.size != 0) {
                    //showLoadingDialog(this)
                    for (i in 0 until imagePathList.size) {
                        if (!imagePathList[i].contains("https:")) {
                            val bitmap =
                                BitmapFactory.decodeFile(imagePathList[i], BitmapFactory.Options())
                            //uploadFirebase2Image(bitmap, i + 1)
                        } else {
                            try {
                                val url = URL(imagePathList[i])
                                val connection: HttpURLConnection =
                                    url.openConnection() as HttpURLConnection
                                connection.setDoInput(true)
                                connection.connect()
                                val input: InputStream = connection.getInputStream()
                                val bitmap = BitmapFactory.decodeStream(input)
                                //uploadFirebase2Image(bitmap, i + 1)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                } else {
                    imagePathList.clear()
                    MainActivity.userDiaryArray[index].diaryArray[day].diaryInfo.imagePathArray.clear()
                    db!!.collection("Diary").document(user!!.email.toString())
                        .collection("DiaryData")
                        .document(MainActivity.userDiaryArray[index].baseData.idx.toString())
                        .collection("DayList")
                        .document(MainActivity.userDiaryArray[index].diaryArray[day].date)
                        .set(MainActivity.userDiaryArray[index].diaryArray[day])
                        .addOnSuccessListener {
                            finish()
                        }
                }
            }
        }

        /*private fun showCustomToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }*/

        fun uploadFirebase2Image(bitmap: Bitmap, count: Int) {
            val storage = Firebase.storage
            val storageRef = storage.reference
            val imageStorageRef =
                storageRef.child("/diary/${user!!.email.toString()}/${MainActivity.userDiaryArray[index].baseData.idx}/day${day + 1}/image${count}.png")
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
                    imagePathList[count - 1] = downloadUri.toString()

                    if (count == imagePathList.size) {
                        MainActivity.userDiaryArray[index].diaryArray[day].diaryInfo.imagePathArray.clear()
                        for (j in 0 until imagePathList.size) {
                            MainActivity.userDiaryArray[index].diaryArray[day].diaryInfo.imagePathArray.add(
                                imagePathList[j]
                            )
                        }
                        db!!.collection("Diary").document(user!!.email.toString())
                            .collection("DiaryData")
                            .document(MainActivity.userDiaryArray[index].baseData.idx.toString())
                            .collection("DayList")
                            .document(MainActivity.userDiaryArray[index].diaryArray[day].date)
                            .set(MainActivity.userDiaryArray[index].diaryArray[day])
                            .addOnSuccessListener {
                                //dismissLoadingDialog()
                                finish()
                            }
                    }
                }
            }
        }

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
}
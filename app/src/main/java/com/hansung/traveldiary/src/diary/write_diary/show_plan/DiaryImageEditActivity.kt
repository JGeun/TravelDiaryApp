package com.hansung.traveldiary.src.diary.write_diary.show_plan

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityDiaryImageEditBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserInfo
import com.hansung.traveldiary.src.diary.write_diary.CountViewModel
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity
import com.hansung.traveldiary.util.LoadingDialog
import com.hansung.traveldiary.util.StatusBarUtil
import java.io.ByteArrayOutputStream

class DiaryImageEditActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDiaryImageEditBinding.inflate(layoutInflater)
    }

    private val diaryImageList = ArrayList<String>()
    private lateinit var getResultImage: ActivityResultLauncher<Intent>
    private lateinit var imagePath: String
    private val countViewModel : CountViewModel by viewModels()
    private val imagePathList = ArrayList<String>()
    lateinit var mLoadingDialog: LoadingDialog

    private var index = 0
    private var day = 0
    private var user : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        user = Firebase.auth.currentUser
        index = intent.getIntExtra("index", 0)
        day = intent.getIntExtra("day", 0)

        val path = MainActivity.userDiaryArray[index].diaryArray[day]
        if(path.diaryInfo.imagePathArray.size != 0){
            val size = path.diaryInfo.imagePathArray.size
            countViewModel.setCount(size)
            for(i in 0 until path.diaryInfo.imagePathArray.size){
                diaryImageList.add(path.diaryInfo.imagePathArray[i])

            }
        }

        binding.editImageRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EditImageAdapter(diaryImageList, countViewModel)
            setHasFixedSize(true)
        }

        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.d("갤러리", "OK")
                imagePath = result.data?.getStringExtra("imagePath")!!
                diaryImageList.add(imagePath)
                countViewModel.countUp()
                binding.editImageRv.adapter?.notifyDataSetChanged()
            }
        }

        binding.addImageBtn.setOnClickListener {
            if(countViewModel.imageCount.value!! >= 5){
                showCustomToast("사진은 최대 5개까지 넣으실 수 있어요")
            }else{
                getResultImage.launch(Intent(this, SelectPictureActivity::class.java))
            }

        }

        binding.tvChecked.setOnClickListener {
            //이미지 메인 데이터에 넣고
            for(i in 0..diaryImageList.size-1) {
//                var imageUri = "drawable://" + diaryImageList.get(i)
//                MainActivity.myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList[day - 1].diaryInfo.imagePathArray.add(imageUri)
//                val bitmap = BitmapFactory.decodeFile(imagePath, BitmapFactory.Options())
//                uploadFirebase2Image(bitmap)
            }
            finish()
        }
    }

    private fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun uploadFirebase2Image(bitmap: Bitmap) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageStorageRef =
            storageRef.child("/diary/${user!!.email.toString()}/${MainActivity.userDiaryArray[index].baseData.idx}/day${day+1}/image${countViewModel.imageCount.value!!}.png")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        showLoadingDialog(this)
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
                imagePathList.add(downloadUri.toString())
                dismissLoadingDialog()
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
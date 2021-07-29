package com.hansung.traveldiary.src.travel

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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hansung.traveldiary.databinding.ActivitySendTravelPlanBinding
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity
import java.io.ByteArrayOutputStream

class SendTravelPlanActivity : AppCompatActivity() {
    private var user : FirebaseUser? = null
    private val binding by lazy {
        ActivitySendTravelPlanBinding.inflate(layoutInflater)
    }

    private lateinit var getResultImage: ActivityResultLauncher<Intent>
    private lateinit var imagePath: String
    private var title = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance().currentUser
        title = intent.getStringExtra("title").toString()

        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if(result.resultCode == RESULT_OK){
                imagePath = result.data?.getStringExtra("imagePath")!!
                Glide.with(this).load(imagePath).circleCrop().into(binding.stpMainImage)

                val op = BitmapFactory.Options()
                var bitmap : Bitmap? = null
                bitmap = BitmapFactory.decodeFile(imagePath, op)
                uploadFirebase2Image(bitmap)
            }
        }

        binding.stpImageView.setOnClickListener{
            getResultImage.launch(Intent(this@SendTravelPlanActivity, SelectPictureActivity::class.java))
        }

        binding.sendPlanBtn.setOnClickListener {
            var title = binding.editTravelTitle.text.toString()
            var hashTag = binding.editHashtag.text.toString()
            var image = binding.stpMainImage.drawable

            val data = LastTripData(image, title, hashTag)
            lastTripList.add(data)
            val position = intent.getIntExtra("position", -1)


            Log.d("sendtrip", "추가")
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
        val imageStorageRef = storageRef.child("/diary/"+user!!.email + "/${title}" + "/mainImage.png")
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

    fun showCustomToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
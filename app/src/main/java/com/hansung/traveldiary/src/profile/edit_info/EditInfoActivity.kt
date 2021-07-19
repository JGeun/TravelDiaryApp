package com.hansung.traveldiary.src.profile.edit_info

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityEditInfoBinding
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity
import com.hansung.traveldiary.util.StatusBarUtil
import java.io.ByteArrayOutputStream

class EditInfoActivity : AppCompatActivity() {
    private lateinit var pref : SharedPreferences
    private val binding : ActivityEditInfoBinding by lazy{
        ActivityEditInfoBinding.inflate(layoutInflater)
    }
    private var originalNickname : String = ""
    private var isChangeName : Boolean = false
    private var isChangeImage : Boolean = false
    private lateinit var getResultImage : ActivityResultLauncher<Intent>
    private lateinit var imagePath : String
    private var user : FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        user = FirebaseAuth.getInstance().currentUser
        pref = getSharedPreferences("user", 0)!!

        originalNickname = FirebaseAuth.getInstance().currentUser!!.displayName.toString()
        binding.editEtNickname.setText(originalNickname)
        binding.editEtNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals(originalNickname)) {
                    isChangeName = false
                } else {
                    isChangeName = true
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        Glide.with(this).load(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.img_bg_profile,
                null
            )
        ).circleCrop().into(binding.editIvProfile)
        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
                if(result.resultCode == RESULT_OK){
                    imagePath = result.data?.getStringExtra("imagePath")!!
                    Glide.with(this).load(imagePath).circleCrop().into(binding.editIvProfile)
                    isChangeImage = true
                }
            }

        binding.editIvProfile.setOnClickListener{
            getResultImage.launch(Intent(this@EditInfoActivity, SelectPictureActivity::class.java))
        }

        binding.editLlOk.setOnClickListener{
            if(isChangeName){
                user = FirebaseAuth.getInstance().currentUser
                val nickname = binding.editEtNickname.text.toString()
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(nickname).build()
                user?.updateProfile(profileUpdates)?.addOnCompleteListener{ task ->
                    if(task.isSuccessful()){
                        showCustomToast("닉네임 변경이 완료되었습니다")
                        Log.d("수정", "닉네임변경 성공")
                        with (pref.edit()) {
                            putString("userName", nickname)
                            commit()
                        }
                        finish()
                    }else{
                        showCustomToast("닉네임 변경이 실패하였습니다")
                        Log.d("수정", "닉네임변경 실패")
                        finish()
                    }
                }
            }
            if(isChangeImage){
                val op = BitmapFactory.Options()
                var bitmap : Bitmap? = null
                bitmap = BitmapFactory.decodeFile(imagePath, op)
                uploadFirebase2Image(bitmap)
            }
        }
    }

    fun uploadFirebase2Image(bitmap: Bitmap) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageStorageRef = storageRef.child("/profileImage/"+user!!.email + "/profileImage.png")
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
                showCustomToast("프로필 이미지가 변경되었습니다.")
                with (pref.edit()) {
                    putString("profileImagePath", downloadUri.toString())
                    commit()
                }
                finish()
            } else {

            }
        }
    }

    fun showCustomToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
package com.hansung.traveldiary.src.profile.edit_info

import android.content.Context
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityEditInfoBinding
import com.hansung.traveldiary.src.UserContents
import com.hansung.traveldiary.src.profile.gallery.SelectPictureActivity
import com.hansung.traveldiary.util.LoadingDialog
import com.hansung.traveldiary.util.StatusBarUtil
import java.io.ByteArrayOutputStream

class EditInfoActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    private val binding: ActivityEditInfoBinding by lazy {
        ActivityEditInfoBinding.inflate(layoutInflater)
    }
    private var originalNickname: String = ""
    private var isChangeName: Boolean = false
    private var isChangeImage: Boolean = false
    private lateinit var getResultImage: ActivityResultLauncher<Intent>
    private lateinit var imagePath: String
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        user = FirebaseAuth.getInstance().currentUser
        db = Firebase.firestore
        pref = getSharedPreferences("user", 0)!!

        binding.editEtNickname.setText(pref.getString("nickname", "날아라 뽀로로-?"))

        val profileImagePath = pref.getString("profileImagePath", "")
        if(profileImagePath != ""){
            Glide.with(this).load(profileImagePath).circleCrop().into(binding.editIvProfile)
        }else{
            Glide.with(this).load(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.img_basic_profile,
                    null
                )
            ).circleCrop().into(binding.editIvProfile)
        }

        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                imagePath = result.data?.getStringExtra("imagePath")!!
                Glide.with(this).load(imagePath).circleCrop().into(binding.editIvProfile)
                isChangeImage = true
            }
        }

        binding.editIvProfile.setOnClickListener {
            getResultImage.launch(Intent(this@EditInfoActivity, SelectPictureActivity::class.java))
        }

        binding.editLlOk.setOnClickListener {
            val nickname = binding.editEtNickname.text.toString()

            showLoadingDialog(this)
            if (isChangeImage) {
                val op = BitmapFactory.Options()
                var bitmap: Bitmap? = null
                bitmap = BitmapFactory.decodeFile(imagePath, op)
                uploadFirebase2Image(bitmap)
            } else {
                val nickname = binding.editEtNickname.text.toString()
                val userRef = db!!.collection("UserInfo").document(user!!.email.toString())
                val userContents = UserContents(nickname, pref.getString("profileImagePath", "").toString())
                userRef.set(userContents)
                with(pref.edit()) {
                    putString("nickname", nickname)
                    commit()
                }
                dismissLoadingDialog()
                finish()
            }
        }

        binding.editIvClear.setOnClickListener {
            finish()
        }
    }

    fun uploadFirebase2Image(bitmap: Bitmap) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val imageStorageRef =
            storageRef.child("/profileImage/" + user!!.email + "/profileImage.png")
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
                val nickname = binding.editEtNickname.text.toString()
                Log.d("체크", downloadUri.toString())
                val userRef = db!!.collection("UserInfo").document(user!!.email.toString())
                val userContents = UserContents(nickname, downloadUri.toString())
                userRef.set(userContents)
                with(pref.edit()) {
                    putString("nickname", nickname)
                    putString("profileImagePath", downloadUri.toString())
                    commit()
                }
                dismissLoadingDialog()
                finish()
            }
        }
    }

    fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
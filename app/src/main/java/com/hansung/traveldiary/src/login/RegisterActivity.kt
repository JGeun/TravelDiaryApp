package com.hansung.traveldiary.src.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.ActivityRegisterBinding
import com.hansung.traveldiary.src.UserContents
import com.hansung.traveldiary.util.LoadingDialog
import com.hansung.traveldiary.util.StatusBarUtil

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private var db: FirebaseFirestore? = null

    var auth: FirebaseAuth? = null
    lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        binding.registerBtn.setOnClickListener {
            if (binding.registerNickname.text.toString() == "") {
                showCustomToast("닉네임을 입력하세요")
                return@setOnClickListener
            } else if (binding.registerId.text.toString().isEmpty()) {
                showCustomToast("이메일을 입력하세요")
                return@setOnClickListener
            } else if (binding.registerPw.text.toString().isEmpty()) {
                showCustomToast("비밀번호를 입력하세요")
                return@setOnClickListener
            } else if (binding.registerPw.text.toString().length < 6) {
                showCustomToast("비밀번호는 6자 이상 입력해주세요")
                return@setOnClickListener
            } else {
                showLoadingDialog(this)
                auth?.createUserWithEmailAndPassword(
                    binding.registerId.text.toString(),
                    binding.registerPw.text.toString()
                )?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val nickname = binding.registerNickname.text.toString()
                        val user = FirebaseAuth.getInstance().currentUser
                        val userContents = UserContents(nickname, "")
                        db!!.collection("UserInfo").document(user!!.email.toString())
                            .set(userContents)
                            .addOnSuccessListener {
                                dismissLoadingDialog()
                                showCustomToast("회원가입이 완료되었습니다")
                                finish()
                            }
                            .addOnFailureListener {
                                dismissLoadingDialog()
                                showCustomToast("회원가입이 실패하였습니다")
                                finish()
                            }
                    }
                }
                dismissLoadingDialog()
            }
        }
        Log.d("가입", "register")
    }

    fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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
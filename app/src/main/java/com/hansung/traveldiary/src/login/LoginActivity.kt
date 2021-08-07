package com.hansung.traveldiary.src.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.ActivityLoginBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserInfo
import com.hansung.traveldiary.util.LoadingDialog
import com.hansung.traveldiary.util.StatusBarUtil

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    var auth: FirebaseAuth? = null
    lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        auth = FirebaseAuth.getInstance()

        db = Firebase.firestore

        binding.signinBtn.setOnClickListener {
            if (binding.inputId.text.toString().isEmpty()) {
                showCustomToast("이메일을 입력하세요")
                return@setOnClickListener
            } else if (binding.inputPw.text.toString().isEmpty()) {
                showCustomToast("비밀번호를 입력하세요")
                return@setOnClickListener
            } else if (binding.inputPw.text.toString().length < 6) {
                showCustomToast("비밀번호는 6자 이상 입력해주세요")
                return@setOnClickListener
            } else {
                showLoadingDialog(this)
                auth?.signInWithEmailAndPassword(
                    binding.inputId.text.toString(),
                    binding.inputPw.text.toString()
                )?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user = Firebase.auth.currentUser
                        println("email: ${user!!.email.toString()}")
                        db!!.collection("UserInfo").document(user!!.email.toString())
                            .get()
                            .addOnSuccessListener { result ->
                                val userInfo = result.toObject<UserInfo>()!!
                                val pref = applicationContext.getSharedPreferences("user", 0)
                                with(pref.edit()) {
                                    putString("login", "success")
                                    putString("nickname", userInfo.nickname)
                                    putString("profileImagePath", userInfo.profileImage)
                                    commit()
                                }
                                dismissLoadingDialog()
                                moveHomePage(task.result?.user)
                            }.addOnFailureListener {
                                dismissLoadingDialog()
                                showCustomToast("실패하였습니다.")
                            }
                    } else {
                        dismissLoadingDialog()
                        showCustomToast("이메일과 비밀번호를 다시 확인해주세요")
                        binding.inputId.setText("")
                        binding.inputPw.setText("")
                    }
                }
            }
        }

        binding.signup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(0, 0)
        }
    }

    fun moveHomePage(user: FirebaseUser?) {
        if (user != null) {
            showCustomToast("로그인 되었습니다")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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
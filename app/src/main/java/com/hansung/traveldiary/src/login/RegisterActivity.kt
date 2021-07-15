package com.hansung.traveldiary.src.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.hansung.traveldiary.databinding.ActivityRegisterBinding
import com.hansung.traveldiary.util.StatusBarUtil

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        auth = FirebaseAuth.getInstance()


        binding.registerBtn.setOnClickListener {
            if (binding.registerId.text.toString().isEmpty()) {
                showCustomToast("이메일을 입력하세요")
                return@setOnClickListener
            } else if (binding.registerPw.text.toString().isEmpty()) {
                showCustomToast("비밀번호를 입력하세요")
                return@setOnClickListener
            } else if (binding.registerPw.text.toString().length < 6) {
                showCustomToast("비밀번호는 6자 이상 입력해주세요")
                return@setOnClickListener
            }else{
                auth?.createUserWithEmailAndPassword(binding.registerId.text.toString(), binding.registerPw.text.toString())
                    ?.addOnCompleteListener {
                            task->
                        if (task.isSuccessful){
                            showCustomToast("회원가입이 완료되었습니다")
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }else{
                            showCustomToast("이미 존재하는 E-Mail/PW 입니다")
                        }
                    }
                Log.d("가입", "register")
            }
        }
    }

    fun showCustomToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
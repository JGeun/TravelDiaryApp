package com.hansung.traveldiary.src.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.hansung.traveldiary.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setSupportActionBar(binding.loginToolbar)

        supportActionBar?.apply {
            title = "회원가입"

            setDisplayHomeAsUpEnabled(true)
        }


        binding.button.setOnClickListener {
            if (binding.signupId.text.toString().isEmpty()){
                Snackbar.make(it, "이메일을 입력하세요", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (binding.signupPw.text.toString().isEmpty()){
                Snackbar.make(it, "비밀번호를 입력하세요", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (binding.signupPw.text.toString().length<6){
                Snackbar.make(it, "비밀번호는 6자 이상 입력해주세요", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth?.createUserWithEmailAndPassword(binding.signupId.text.toString(), binding.signupPw.text.toString())
                ?.addOnCompleteListener {
                    task->
                    if (task.isSuccessful){
                        Snackbar.make(it, "회원가입이 완료되었습니다", Snackbar.LENGTH_SHORT)
                        startActivity(Intent(this, LoginActivity::class.java))
                    }else{
                        Snackbar.make(it, "이미 존재하는 E-Mail/PW 입니다", Snackbar.LENGTH_SHORT)
                    }
                }
            Log.d("가입", "register")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
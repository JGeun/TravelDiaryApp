package com.hansung.traveldiary.src.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hansung.traveldiary.databinding.ActivityLoginBinding
import com.hansung.traveldiary.src.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signinBtn.setOnClickListener {
            if (binding.inputId.text.toString().isEmpty()){
                Snackbar.make(it, "이메일을 입력하세요", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (binding.inputPw.text.toString().isEmpty()){
                Snackbar.make(it, "비밀번호를 입력하세요", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (binding.inputPw.text.toString().length<6){
                Snackbar.make(it, "비밀번호는 6자 이상 입력해주세요", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth?.signInWithEmailAndPassword(binding.inputId.text.toString(), binding.inputPw.text.toString())
                ?.addOnCompleteListener {
                    task->
                    if (task.isSuccessful){
                        moveHomePage(task.result?.user)
                    }
                    else{
                        Snackbar.make(it, "이메일과 비밀번호를 다시 확인해주세요", Snackbar.LENGTH_SHORT).show()
                        binding.inputId.setText("")
                        binding.inputPw.setText("")
                    }
                }
        }

        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun moveHomePage(user: FirebaseUser?){
        if (user!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
package com.hansung.traveldiary.src.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.ActivityRegisterBinding
import com.hansung.traveldiary.src.FriendList
import com.hansung.traveldiary.src.UserInfo
import com.hansung.traveldiary.src.UserList
import com.hansung.traveldiary.util.LoadingDialog
import com.hansung.traveldiary.util.StatusBarUtil
import com.google.firebase.auth.UserInfo as UserInfo1

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private var db: FirebaseFirestore? = null
    private var userList = UserList()
    var auth: FirebaseAuth? = null
    lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        val userDataRef = db!!.collection("UserData").document("UserEmail")
        userDataRef.get()
            .addOnSuccessListener { result->
                val data = result.toObject<UserList>()
                if(data != null) userList = data
            }

        binding.registerBtn.setOnClickListener {
            val email = binding.registerId.text.toString()
            if (binding.registerNickname.text.toString() == "") {
                showCustomToast("???????????? ???????????????")
                return@setOnClickListener
            } else if (email.isEmpty()) {
                showCustomToast("???????????? ???????????????")
                return@setOnClickListener
            } else if (binding.registerPw.text.toString().isEmpty()) {
                showCustomToast("??????????????? ???????????????")
                return@setOnClickListener
            } else if (binding.registerPw.text.toString().length < 6) {
                showCustomToast("??????????????? 6??? ?????? ??????????????????")
                return@setOnClickListener
            }else if(userList.emailFolder.contains(email)){
                showCustomToast("?????? ???????????? ??????????????????.")
                return@setOnClickListener
            }else {
                auth?.createUserWithEmailAndPassword(
                    binding.registerId.text.toString(),
                    binding.registerPw.text.toString()
                )?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showLoadingDialog(this)
                        binding.registerBtn.isEnabled = false
                        userList.emailFolder.add(email)
                        userDataRef.set(userList)
                        val nickname = binding.registerNickname.text.toString()
                        val user = FirebaseAuth.getInstance().currentUser
                        val userInfo = UserInfo(email, nickname, "", FriendList())
                        db!!.collection("UserInfo").document(user!!.email.toString())
                            .set(userInfo)
                            .addOnSuccessListener {
                                dismissLoadingDialog()
                                showCustomToast("??????????????? ?????????????????????")
                                finish()
                            }
                            .addOnFailureListener {
                                dismissLoadingDialog()
                                showCustomToast("??????????????? ?????????????????????")
                                finish()
                            }
                    }
                }
            }
        }
        Log.d("??????", "register")
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
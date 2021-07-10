package com.hansung.traveldiary.src.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.FragmentProfileBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.login.LoginActivity
import com.hansung.traveldiary.src.profile.edit_info.EditInfoActivity

class ProfileFragment : Fragment(){
    private lateinit var pref : SharedPreferences
    private lateinit var binding : FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        pref = context?.getSharedPreferences("login", 0)!!

        binding.profileLlEdit.setOnClickListener{
            startActivity(Intent(context, EditInfoActivity::class.java))
        }
        binding.changePWSetting.setOnClickListener{
        }
        logout()
        deleteUser()
        return binding.root
    }

    private fun logout(){
        binding.logoutSetting.setOnClickListener{
            with(pref!!.edit()){
                putString("login", "fail")
                commit()
            }
            val user = Firebase.auth
            user.signOut()
            showCustomToast("로그아웃 되었습니다")
            startActivity(Intent(context, LoginActivity::class.java))
            (context as MainActivity).finish()
        }
    }

    private fun deleteUser(){
        binding.deleteUserSetting.setOnClickListener{
            if(pref!!.getString("login", "").equals("success")){

                val user = Firebase.auth.currentUser

                user?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showCustomToast("회원탈퇴 되었습니다")

                        with (pref.edit()) {
                            putString("login", "fail")
                            commit()
                        }
                        startActivity(Intent(context, LoginActivity::class.java))
                        (context as MainActivity).finish()
                    }else{
                        showCustomToast("실패")
                    }
                }?.addOnFailureListener{
                    showCustomToast("실패")
                }
            }
        }
    }
    private fun showCustomToast(message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
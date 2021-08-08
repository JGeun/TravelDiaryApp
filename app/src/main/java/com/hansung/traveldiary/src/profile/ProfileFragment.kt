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
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentProfileBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.login.LoginActivity
import com.hansung.traveldiary.src.profile.edit_info.EditInfoActivity

class ProfileFragment : Fragment() {
    private lateinit var pref: SharedPreferences
    private lateinit var binding: FragmentProfileBinding

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        user = FirebaseAuth.getInstance().currentUser
        db = Firebase.firestore

        pref = context?.getSharedPreferences("user", 0)!!

        binding.userName.text = FirebaseAuth.getInstance().currentUser?.displayName ?: "null"
        binding.profileLlEdit.setOnClickListener {
            startActivity(Intent(context, EditInfoActivity::class.java))
        }
        binding.changePWSetting.setOnClickListener {
        }
        logout()
        deleteUser()

        binding.planCount.text = MainActivity.userPlanArray.size.toString()
        binding.diaryCount.text = MainActivity.userDiaryArray.size.toString()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val userName = pref.getString("nickname", "")
        if (userName.equals("")) {
            binding.userName.text = user?.displayName ?: "null"
        } else {
            binding.userName.text = userName
        }

        val profileImagePath = pref.getString("profileImagePath", "")
        if (profileImagePath.equals("")) {
            Glide.with(requireContext()).load(ResourcesCompat.getDrawable(resources, R.drawable.img_basic_profile, null)).into(binding.userProfileImage)
        } else {
            Glide.with(requireContext()).load(profileImagePath).into(binding.userProfileImage)
        }
    }

    private fun logout() {
        binding.logoutSetting.setOnClickListener {
            MainActivity.showLoadingDialog(context as MainActivity)

            with(pref!!.edit()) {
                putString("login", "fail")
                putString("nickname", "")
                putString("profileImagePath", "")
                commit()
            }
            val user = Firebase.auth
            user.signOut()
            showCustomToast("로그아웃 되었습니다")
            startActivity(Intent(context, LoginActivity::class.java))
            (context as MainActivity).finish()
        }
    }

    private fun deleteUser() {
        binding.deleteUserSetting.setOnClickListener {
            if (pref.getString("login", "").equals("success")) {

                val user = Firebase.auth.currentUser

                user?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showCustomToast("회원탈퇴 되었습니다")

                        with(pref.edit()) {
                            putString("login", "fail")
                            putString("profileImagePath", "")
                            commit()
                        }
                        startActivity(Intent(context, LoginActivity::class.java))
                        (context as MainActivity).finish()
                    } else {
                        showCustomToast("실패")
                    }
                }?.addOnFailureListener {
                    showCustomToast("실패")
                }
            }
        }
    }

    private fun showCustomToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
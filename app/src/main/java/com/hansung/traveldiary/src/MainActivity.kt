package com.hansung.traveldiary.src

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hansung.traveldiary.src.home.HomeFragment
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMainBinding
import com.hansung.traveldiary.src.bulletin.BulletinFragment
import com.hansung.traveldiary.src.profile.ProfileFragment
import com.hansung.traveldiary.src.travel.TravelBaseFragment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object{
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref = applicationContext.getSharedPreferences("login", 0)
        Log.d("MainActivity", pref.getString("login", "")!!)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.main_btm_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "home")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_bulletin -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, BulletinFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "bulletin")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_travel -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TravelBaseFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "travel")
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.main_btm_profile -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ProfileFragment())
                            .commitAllowingStateLoss()
                        Log.d("확인", "profile")
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })
    }
}


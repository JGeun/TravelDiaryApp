package com.hansung.traveldiary.src.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hansung.traveldiary.databinding.FragmentHomeBinding
import com.readystatesoftware.systembartint.SystemBarTintManager


class HomeFragment : Fragment(){
    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        activity?.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        activity?.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN }

//        // create our manager instance after the content view is set
//        val tintManager = SystemBarTintManager(activity)
//        // enable status bar tint\
//        tintManager.isStatusBarTintEnabled = true
//        // enable navigation bar tint
//        tintManager.setNavigationBarTintEnabled(true)
//
//        // StatusBar를 20% 투명도를 가지게 합니다.
//        tintManager.setTintColor(Color.parseColor("#00000000"));


        return binding.root
    }

    //status bar의 높이 계산
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) result = resources.getDimensionPixelSize(resourceId)
        return result
    }
}
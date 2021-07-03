package com.hansung.traveldiary.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import com.hansung.traveldiary.R
class StatusBarUtil {
    enum class StatusBarColorType(val backgroundColorId: Int) {
        // 색 지정
        WHITE_STATUS_BAR(R.color.white),
        DEFAULT_STATUS_BAR(R.color.white),
        DIARY_SECTION_STATUS_BAR(R.color.diary_section_bgcolor),
        MAIN_STATUS_BAR(R.color.maincolor)
    }

    companion object{
        public fun setStatusBarColor(activity: Activity, colorType: StatusBarColorType){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                    activity.window.insetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                else
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }else{
                // 검은색
                activity.window.decorView.systemUiVisibility = 0
            }

            // 상태바 배경 색 지정
            activity.window.statusBarColor =
                ContextCompat.getColor(activity, colorType.backgroundColorId)
        }
    }
}
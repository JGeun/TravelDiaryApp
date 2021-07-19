package com.hansung.traveldiary.src.travel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.hansung.traveldiary.R

class AddPlanDialog(context: Context){
    private val dlg = Dialog(context)

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_add_plan)
        dlg.setCancelable(true)
        dlg.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dlg.show()
    }
}
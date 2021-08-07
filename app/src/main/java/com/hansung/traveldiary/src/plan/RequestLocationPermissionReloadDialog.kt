package com.hansung.traveldiary.src.plan

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import android.view.Window
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.content.ContextCompat.startActivity
import com.hansung.traveldiary.R
import com.naver.maps.map.e

class RequestLocationPermissionReloadDialog(context: Context){
    private val dlg = Dialog(context)
    private val parentContext = context
    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_location_permission_reload)
        dlg.findViewById<TextView>(R.id.dg_lp_ok).setOnClickListener{
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + parentContext.getPackageName()));
                parentContext.startActivity(intent);
            } catch (e : ActivityNotFoundException) {
                e.printStackTrace();
                val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                parentContext.startActivity(intent);
            }
        }
        dlg.findViewById<TextView>(R.id.dg_lp_cancel).setOnClickListener{
            dlg.dismiss()
            (parentContext as TravelPlanBaseActivity).finish()
        }
        dlg.setCancelable(true)
        dlg.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dlg.show()
    }
}
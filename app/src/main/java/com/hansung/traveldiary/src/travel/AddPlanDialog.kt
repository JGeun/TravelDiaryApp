package com.hansung.traveldiary.src.travel

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentTravelPlanSectionBinding
import java.util.*
import kotlin.collections.ArrayList

class AddPlanDialog(context: Context) {
    private val dlg = Dialog(context)


    fun start(fragment: TravelPlanSectionFragment, tripPlanList: ArrayList<PlanSectionData>){
        val binding = FragmentTravelPlanSectionBinding.inflate(LayoutInflater.from(dlg.context))

//        dlg.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_add_plan)
        dlg.setCancelable(true)
        dlg.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dlg.show()

        dlg.findViewById<EditText>(R.id.edit_start_date).setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    dlg.findViewById<EditText>(R.id.edit_start_date).setText(String.format("$y - %02d - %02d", m+1, d))
                }

                val datePickerDialog =
                    DatePickerDialog(dlg.context, listener, year, month, day)
                datePickerDialog.show()
            }
        }

        dlg.findViewById<EditText>(R.id.edit_end_date).setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    dlg.findViewById<EditText>(R.id.edit_end_date).setText(String.format("$y - %02d - %02d", m+1, d))
                }

                val datePickerDialog =
                    DatePickerDialog(dlg.context, listener, year, month, day)
                datePickerDialog.show()
            }
        }

        var color = "null"
        val bookColor = dlg.findViewById<RadioGroup>(R.id.addPlan_rg_bookcolor)
        bookColor.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.addPlen_rb_pink -> color = "pink"
                R.id.addPlen_rb_blue -> color = "blue"
                R.id.addPlen_rb_green -> color = "green"
                R.id.addPlen_rb_olive -> color = "olive"
                R.id.addPlen_rb_purple -> color = "purple"
            }
            Log.d("확인", bookColor.checkedRadioButtonId.toString())
        }

        dlg.findViewById<Button>(R.id.addPlan_btn).setOnClickListener {
            var title = dlg.findViewById<EditText>(R.id.edit_title).text.toString()
            var startDate = dlg.findViewById<EditText>(R.id.edit_start_date).text.toString()
            var endDate = dlg.findViewById<EditText>(R.id.edit_end_date).text.toString()


            println(startDate + " " + endDate)
            /*val data = PlanSectionData(
                    ResourcesCompat.getDrawable(
                        dlg.context.resources,
                        R.drawable.ic_diary_blue,
                        null
                    )!!, title, color, startDate, endDate
                )
            fragment.addPlanAndNotify(data)*/

            println("size : " + tripPlanList.size)
//            binding.plantripRv.adapter?.notifyDataSetChanged()
            Log.d("추가", "rv")

            dlg.dismiss()
        }

    }
}
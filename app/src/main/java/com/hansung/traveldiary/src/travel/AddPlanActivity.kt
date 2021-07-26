package com.hansung.traveldiary.src.travel

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityAddPlanBinding
import com.hansung.traveldiary.src.plan.model.DayInfo
import com.hansung.traveldiary.src.plan.model.PlanTotalData
import java.text.SimpleDateFormat
import java.util.*

class AddPlanActivity : AppCompatActivity() {
    private val binding: ActivityAddPlanBinding by lazy {
        ActivityAddPlanBinding.inflate(layoutInflater)
    }
    private val TAG = "AddPlanActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editStartDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    binding.editStartDate.setText(String.format("$y-%02d-%02d", m + 1, d))
                }

                val datePickerDialog =
                    DatePickerDialog(this, listener, year, month, day)
                datePickerDialog.show()
            } else {
                binding.editStartDate.clearFocus()
            }
        }

        binding.editEndDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    binding.editEndDate.setText(String.format("$y-%02d-%02d", m + 1, d))
                }

                val datePickerDialog =
                    DatePickerDialog(this, listener, year, month, day)
                datePickerDialog.show()
            } else {
                binding.editStartDate.clearFocus()
            }
        }

        var color = "pink"
        binding.addPlanRgBookcolor.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.addPlen_rb_pink -> color = "pink"
                R.id.addPlen_rb_blue -> color = "blue"
                R.id.addPlen_rb_green -> color = "green"
                R.id.addPlen_rb_olive -> color = "olive"
                R.id.addPlen_rb_purple -> color = "purple"
            }
        }

        binding.addPlanBtn.setOnClickListener {
            var title = binding.editTitle.text.toString()
            var startDate = binding.editStartDate.text.toString()
            var endDate = binding.editEndDate.text.toString()

            val user = Firebase.auth.currentUser
            val db = Firebase.firestore
            var dayList = ArrayList<DayInfo>()

            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val startDateFormat = simpleDateFormat.parse("$startDate 00:00:00")!!
            val endDateFormat = simpleDateFormat.parse("$endDate 00:00:00")!!
            val calcDate = ((endDateFormat.time- startDateFormat.time) / (60*60*24*1000)).toInt()

            for(i in 0 .. calcDate){
                dayList.add(DayInfo(afterDate(startDate, i), arrayListOf()))
            }
            var planTotalData = PlanTotalData(color, startDate, endDate, dayList)

            db!!.collection(user!!.email.toString()).document(title.toString())
                .set(planTotalData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error writing document", e)
                    Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
                }
        }

        binding.apaOutblock.setOnClickListener {
            println("아웃")
            finish()
            overridePendingTransition(0, 0)
        }

        binding.apaMainblock.setOnClickListener {
            println("메인")
        }
    }

    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }
}
package com.hansung.traveldiary.src.travel

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityAddTravelPlanBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.PlanBookData
import com.hansung.traveldiary.src.plan.model.DayInfo
import com.hansung.traveldiary.src.plan.model.PlanTotalData
import java.text.SimpleDateFormat
import java.util.*

class AddTravelPlanActivity : AppCompatActivity() {
    private val binding: ActivityAddTravelPlanBinding by lazy {
        ActivityAddTravelPlanBinding.inflate(layoutInflater)
    }
    private val TAG = "AddPlanActivity"
    private var color = "pink"
    var date = ""
    var startdate = ""
    var enddate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.icDatepicker.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                startdate = String.format("$y-%02d-%02d", m + 1, d)
                date += String.format("$y-%02d-%02d", m + 1, d)
                Log.d("시작날짜", date)
                binding.editDate.setText(date)
                Log.d("달력", "OK")
                showDatepicker()
            }

            val datePickerDialog =
                DatePickerDialog(this, listener, year, month, day)
            datePickerDialog.show()
        }

        binding.editPlace.setOnClickListener {
            val bottomDialog = PlanlistBottomDialog()
            bottomDialog.show(supportFragmentManager, "bottomPlanlistSheet")
        }

        setRadioButton()

        binding.addPlanBtn.setOnClickListener {
            var title = binding.editTitle.text.toString()
            var startDate = startdate
            var endDate = enddate

            val user = Firebase.auth.currentUser
            val db = Firebase.firestore
            var dayList = ArrayList<DayInfo>()

            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val startDateFormat = simpleDateFormat.parse("$startDate 00:00:00")!!
            val endDateFormat = simpleDateFormat.parse("$endDate 00:00:00")!!
            val calcDate =
                ((endDateFormat.time - startDateFormat.time) / (60 * 60 * 24 * 1000)).toInt()

            var planTotalData = PlanTotalData(color, startDate, endDate, dayList)
            MainActivity.planBookList.add(PlanBookData(title, planTotalData))

            val docPlanRef = db.collection(user!!.email.toString()).document("plan")
            docPlanRef.set("title")
//            for (i in 0..calcDate) {
//                dayList.add(DayInfo(afterDate(startDate, i), arrayListOf()))
//            }

//            db!!.collection(user!!.email.toString()).document(title)
//                .set(planTotalData)
//                .addOnSuccessListener {
//                    Log.d(TAG, "DocumentSnapshot successfully written!")
//                    setResult(RESULT_OK)
//                    finish()
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "Error writing document", e)
//                    Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
//                }

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

    fun setRadioButton() {
        binding.addPlanRbPink.setOnClickListener {
            println("pink")
            color = "pink"
            binding.addPlanRbPink.isChecked = true
            binding.addPlanRbBlue.isChecked = false
            binding.addPlanRbYellow.isChecked = false
            binding.addPlanRbSky.isChecked = false
            binding.addPlanRbPurple.isChecked = false
            binding.addPlanRbOrange.isChecked = false
        }
        binding.addPlanRbBlue.setOnClickListener {
            println("blue")
            color = "blue"
            binding.addPlanRbPink.isChecked = false
            binding.addPlanRbBlue.isChecked = true
            binding.addPlanRbYellow.isChecked = false
            binding.addPlanRbSky.isChecked = false
            binding.addPlanRbPurple.isChecked = false
            binding.addPlanRbOrange.isChecked = false
        }
        binding.addPlanRbYellow.setOnClickListener {
            println("yellow")
            color = "yellow"
            binding.addPlanRbPink.isChecked = false
            binding.addPlanRbBlue.isChecked = false
            binding.addPlanRbYellow.isChecked = true
            binding.addPlanRbSky.isChecked = false
            binding.addPlanRbPurple.isChecked = false
            binding.addPlanRbOrange.isChecked = false
        }
        binding.addPlanRbSky.setOnClickListener {
            println("sky")
            color = "sky"
            binding.addPlanRbPink.isChecked = false
            binding.addPlanRbBlue.isChecked = false
            binding.addPlanRbYellow.isChecked = false
            binding.addPlanRbSky.isChecked = true
            binding.addPlanRbPurple.isChecked = false
            binding.addPlanRbOrange.isChecked = false
        }

        binding.addPlanRbPurple.setOnClickListener {
            println("purple")
            color = "purple"
            binding.addPlanRbPink.isChecked = false
            binding.addPlanRbBlue.isChecked = false
            binding.addPlanRbYellow.isChecked = false
            binding.addPlanRbSky.isChecked = false
            binding.addPlanRbPurple.isChecked = true
            binding.addPlanRbOrange.isChecked = false
        }

        binding.addPlanRbOrange.setOnClickListener {
            println("orange")
            color = "orange"
            binding.addPlanRbPink.isChecked = false
            binding.addPlanRbBlue.isChecked = false
            binding.addPlanRbYellow.isChecked = false
            binding.addPlanRbSky.isChecked = false
            binding.addPlanRbPurple.isChecked = false
            binding.addPlanRbOrange.isChecked = true
        }

    }

    fun showDatepicker() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        var listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            enddate = String.format("$y-%02d-%02d", m + 1, d)
            date += " ~ " + String.format("$y-%02d-%02d", m + 1, d)
            Log.d("끝날짜", date)
            binding.editDate.setText(date)
        }

        val datePickerDialog =
            DatePickerDialog(this, listener, year, month, day)
        datePickerDialog.show()
    }

}

package com.hansung.traveldiary.src.plan.plan_day_section

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.ActivityPlanDaySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.PlaceInfoFolder2
import com.hansung.traveldiary.src.diary.SendTravelPlanActivity
import com.hansung.traveldiary.util.StatusBarUtil
import java.util.*

class PlanDaySectionActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPlanDaySectionBinding.inflate(layoutInflater)
    }
    private var index: Int = 0
    private var title: String? = null
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private var placeInfoFolder: PlaceInfoFolder2 = PlaceInfoFolder2()
    private var color: String? = null

    private var count = 1

    private val TAG = "PlanAddDayActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setStatusBarColor(
            this,
            StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR
        )

        index = intent.getIntExtra("index", 0)
        Log.d("체크", "DaySection color: ${color}")

        binding.addDayTitle.text = MainActivity.userPlanArray[index].planBaseData.title

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter = PlanDaySectionAdapter(index)
            layoutManager = LinearLayoutManager(this@PlanDaySectionActivity)
        }


        binding.dsIvBack.setOnClickListener {
            finish()
        }

        binding.dsIvSend.setOnClickListener {
            val intent = Intent(this, SendTravelPlanActivity::class.java)
            intent.putExtra("title", title)
            startActivity(intent)
        }
    }
}
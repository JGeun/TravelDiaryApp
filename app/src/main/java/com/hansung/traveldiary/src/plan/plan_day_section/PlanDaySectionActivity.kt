package com.hansung.traveldiary.src.plan.plan_day_section

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.ActivityPlanDaySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.plan.model.DayInfo
import com.hansung.traveldiary.src.plan.model.PlanTotalData
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel
import com.hansung.traveldiary.src.travel.SendTravelPlanActivity
import com.hansung.traveldiary.util.StatusBarUtil
import java.util.*

class PlanDaySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityPlanDaySectionBinding.inflate(layoutInflater)
    }
    private var title : String? = null
    private var user : FirebaseUser? = null
    private var db : FirebaseFirestore? = null
    private var planTotalData : PlanTotalData = PlanTotalData()
    private var dayList  = ArrayList<DayInfo>()
    private val userPlanDataModel : SharedPlaceViewModel by viewModels()

    private var count = 1

    private val TAG = "PlanAddDayActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)

        title = intent.getStringExtra("title")
        if(title == ""){
            binding.addDayTitle.text = "영진이의 부산여행"
        }else{
            binding.addDayTitle.text = title
        }

        user = Firebase.auth.currentUser
        db = Firebase.firestore
        db!!.collection(user!!.email.toString()).document(title.toString())
            .get().addOnSuccessListener  { documentSnapshot ->
                val data = documentSnapshot.toObject<PlanTotalData>()!!
                planTotalData = data

                if(planTotalData.dayList.size == 0){
                    binding.addDayNoMsg.isVisible = true
                    binding.dsRecyclerview.isVisible = false
                }else{
                    binding.addDayNoMsg.isVisible = false
                    binding.dsRecyclerview.isVisible = true
                }

                binding.dsRecyclerview.apply {
                    setHasFixedSize(true)
                    adapter= PlanDaySectionAdapter(planTotalData)
                    layoutManager= LinearLayoutManager(this@PlanDaySectionActivity)
                }
            }



        binding.dsIvBack.setOnClickListener{
            finish()
        }

        binding.dsIvSend.setOnClickListener {
            val intent = Intent(this, SendTravelPlanActivity::class.java)
            val pos = this.intent.getIntExtra("pos", -1)
            Log.d("인덱스", "$pos")
            intent.putExtra("position", pos)
            startActivity(intent)
        }

    }

    fun getTitleContents() : String?{
        return title
    }
}
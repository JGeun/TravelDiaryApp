package com.hansung.traveldiary.src.plan.add_day

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.ActivityPlanAddDayBinding
import com.hansung.traveldiary.src.plan.model.DayInfo
import com.hansung.traveldiary.src.plan.model.PlaceInfo
import com.hansung.traveldiary.src.plan.model.PlanTotalData
import com.hansung.traveldiary.util.StatusBarUtil
import java.util.*

class PlanAddDayActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityPlanAddDayBinding.inflate(layoutInflater)
    }
    private var title : String? = null
    private var user : FirebaseUser? = null
    private var db : FirebaseFirestore? = null
    private var planTotalData : PlanTotalData = PlanTotalData()
    private var dayList  = ArrayList<DayInfo>()

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
                    adapter= DayAddAdapter(planTotalData)
                    layoutManager= LinearLayoutManager(this@PlanAddDayActivity)
                }
            }

//        binding.addDayFab.setOnClickListener{
//            if(planTotalData.dayList.size == 0){
//                binding.addDayNoMsg.isVisible = false
//                binding.dsRecyclerview.isVisible = true
//            }
//            planTotalData.dayList.add(DayInfo("2021-07-25", ArrayList<PlaceInfo>()))
//            planTotalData.dayList[0].placeInfoArray.add(PlaceInfo("dd", 12.2, 22.2))
//
//            val testData = HashMap<String, PlanTotalData>()
//            testData["day"] = planTotalData
//
//            db!!.collection(user!!.email.toString()).document(title.toString())
//                .set(testData)
//                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
//
//            binding.dsRecyclerview.adapter!!.notifyDataSetChanged()
//        }

        binding.dsIvBack.setOnClickListener{
            finish()
        }


    }

    fun getTitleContents() : String?{
        return title
    }
}
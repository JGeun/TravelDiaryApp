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
import com.hansung.traveldiary.src.PlaceInfoFolder
import com.hansung.traveldiary.src.diary.SendTravelPlanActivity
import com.hansung.traveldiary.util.StatusBarUtil
import java.util.*

class PlanDaySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityPlanDaySectionBinding.inflate(layoutInflater)
    }
    private var title : String? = null
    private var user : FirebaseUser? = null
    private var db : FirebaseFirestore? = null
    private var placeInfoFolder : PlaceInfoFolder = PlaceInfoFolder()
    private var color : String? = null

    private var count = 1

    private val TAG = "PlanAddDayActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)

        title = intent.getStringExtra("title")
        color = intent.getStringExtra("color")
        Log.d("체크", "DaySection color: ${color}")

        if(title == ""){
            binding.addDayTitle.text = "영진이의 부산여행"
        }else{
            binding.addDayTitle.text = title
        }

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        val userDocRef = db!!.collection("User").document("UserData")
        userDocRef.collection(user!!.email.toString()).document("Plan").collection(title!!).document("PlaceInfo")
            .get().addOnSuccessListener  { documentSnapshot ->
                placeInfoFolder = documentSnapshot.toObject<PlaceInfoFolder>()!!

                if(placeInfoFolder.dayPlaceList.size == 0){
                    binding.addDayNoMsg.isVisible = true
                    binding.dsRecyclerview.isVisible = false
                }else{
                    binding.addDayNoMsg.isVisible = false
                    binding.dsRecyclerview.isVisible = true
                }

                binding.dsRecyclerview.apply {
                    setHasFixedSize(true)
                    adapter= PlanDaySectionAdapter(placeInfoFolder, color)
                    layoutManager= LinearLayoutManager(this@PlanDaySectionActivity)
                }
            }

        binding.dsIvBack.setOnClickListener{
            finish()
        }

        binding.dsIvSend.setOnClickListener {
            val intent = Intent(this, SendTravelPlanActivity::class.java)
            intent.putExtra("title", title)
            startActivity(intent)
        }

    }

    fun getTitleContents() : String?{
        return title
    }

    fun getColor() : String?{
        return color
    }

}
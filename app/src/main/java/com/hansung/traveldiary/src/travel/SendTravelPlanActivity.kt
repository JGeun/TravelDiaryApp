package com.hansung.traveldiary.src.travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivitySendTravelPlanBinding
import com.hansung.traveldiary.src.travel.adapter.SendTripAdapter

class SendTravelPlanActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySendTravelPlanBinding.inflate(layoutInflater)
    }
//    private val lastTripList = ArrayList<LastTripData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.sendPlanBtn.setOnClickListener {
            var title = binding.editTravelTitle.text.toString()
            var hashTag = binding.editHashtag.text.toString()
            var image = binding.editImage.drawable

            val data = LastTripData(image, title, hashTag)
            lastTripList.add(data)
            val position = intent.getIntExtra("position", -1)
            Log.d("POSITION", "$position" )

            Log.d("sendtrip", "추가")
            finish()
        }
    }

}
package com.hansung.traveldiary.src.travel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.FragmentTravelPlanSectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.travel.adapter.PlanSectionAdapter
import com.naver.maps.map.util.FusedLocationSource


class TravelPlanSectionFragment : Fragment() {
    private lateinit var binding: FragmentTravelPlanSectionBinding
    private lateinit var mLocationSource: FusedLocationSource

    private var title: String? = null
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    private val TAG = "TravelPlanSection"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTravelPlanSectionBinding.inflate(inflater, container, false)

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        binding.floatingActionButton.setOnClickListener {
            (context as MainActivity).makePlanBook()
        }

        if (MainActivity.userPlanArray.size == 0) {
            binding.planSectionNoPlan.isVisible = true
            binding.planSectionRecyclerView.isVisible = false
        } else {
            binding.planSectionNoPlan.isVisible = false
            binding.planSectionRecyclerView.isVisible = true
        }
        println("TravelPlanSection 들어옴")

        MainActivity.userPlanArray.sortBy { it.planBaseData.startDate }

        for (i in 0 until MainActivity.userPlanArray.size) {
            Log.d("정렬중", MainActivity.userPlanArray[i].planBaseData.title + " / " + MainActivity.userPlanArray[i].planBaseData.startDate)
        }

        binding.planSectionRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = PlanSectionAdapter(MainActivity.userPlanArray)
        }


        val onScrollListener = object : RecyclerView.OnScrollListener() {
            var temp: Int = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (temp == 1) {
                    super.onScrolled(recyclerView, dx, dy)
                    binding.floatingActionButton.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                binding.floatingActionButton.show()
                temp = 1
            }
        }
        binding.planSectionRecyclerView.addOnScrollListener(onScrollListener)

        return binding.root
    }

    fun newInstant(): TravelPlanSectionFragment {
        val args = Bundle()
        val frag = TravelPlanSectionFragment()
        frag.arguments = args
        return frag
    }
}
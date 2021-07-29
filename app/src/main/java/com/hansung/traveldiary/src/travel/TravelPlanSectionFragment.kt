package com.hansung.traveldiary.src.travel

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.databinding.FragmentTravelPlanSectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.plan.model.DayInfo
import com.hansung.traveldiary.src.plan.model.PlanTotalData
import com.hansung.traveldiary.src.travel.adapter.PlanSectionAdapter
import com.naver.maps.map.util.FusedLocationSource


data class PlanSectionData(
    val image: Drawable,
    val title: String,
    val color: String,
    val start_date: String,
    val end_date: String
)



class TravelPlanSectionFragment : Fragment() {
    private lateinit var binding: FragmentTravelPlanSectionBinding
    private lateinit var mLocationSource: FusedLocationSource
    private val tripPlanList = ArrayList<PlanSectionData>()

    private lateinit var planTotalData: PlanTotalData
    private var dayList = java.util.ArrayList<DayInfo>()

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
//            val dlg = AddPlanDialog(requireContext())
//            dlg.start(this, tripPlanList)

            (context as MainActivity).makePlanBook()
        }

        MainActivity.planBookList.sortBy { it.planTotalData.startDate }
        for(list in MainActivity.planBookList){
            println(list.title)
        }

        binding.plantripRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = PlanSectionAdapter(MainActivity.planBookList)
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

        binding.plantripRv.addOnScrollListener(onScrollListener)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        println("TravelPlanSection start")
    }

    fun deletePlan(position: Int){
        tripPlanList.removeAt(position)
        binding.plantripRv.adapter!!.notifyDataSetChanged()
    }


    fun newInstant(): TravelPlanSectionFragment {
        val args = Bundle()
        val frag = TravelPlanSectionFragment()
        frag.arguments = args
        return frag
    }
}
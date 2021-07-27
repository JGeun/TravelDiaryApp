package com.hansung.traveldiary.src.travel

import android.R
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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

data class PlanBookData(var title: String, var planTotalData: PlanTotalData)

class TravelPlanSectionFragment : Fragment() {
    private lateinit var binding: FragmentTravelPlanSectionBinding
    private lateinit var mLocationSource: FusedLocationSource
    private val tripPlanList = ArrayList<PlanSectionData>()
    private val planBookList = ArrayList<PlanBookData>()

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

            (context as MainActivity).startActivity(
                Intent(
                    context,
                    AddTravelPlanActivity::class.java
                )
            )
        }

        binding.plantripRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = PlanSectionAdapter(planBookList)
        }

        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator() //add this
        fadeIn.duration = 1000

        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator() //and this
        fadeOut.startOffset = 1000
        fadeOut.duration = 1000

        val animation = AnimationSet(false) //change to false
        //animation.addAnimation(fadeIn)
        animation.addAnimation(fadeOut)

        val onScrollListener = object:RecyclerView.OnScrollListener() {
            var temp: Int = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(temp == 1) {
                    super.onScrolled(recyclerView, dx, dy)

                    binding.floatingActionButton.animation = fadeIn
                    binding.floatingActionButton.isVisible = false
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                binding.floatingActionButton.animation = fadeOut
                binding.floatingActionButton.isVisible = true
                temp = 1
            }
        }

        binding.plantripRv.addOnScrollListener(onScrollListener)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        planBookList.clear()

        val dbCollection = db!!.collection(user!!.email.toString())
        dbCollection
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val docRef = dbCollection.document(document.id)
                        .get().addOnSuccessListener { documentSnapshot ->
                            val data = documentSnapshot.toObject<PlanTotalData>()!!
                            planBookList.add(PlanBookData(document.id, data))
                            println(data.toString())
                            println("안쪽" + planBookList.size.toString())
                            binding.plantripRv.adapter!!.notifyDataSetChanged()
                        }.addOnFailureListener { exception ->
                            Log.d(TAG, "Error getting documents: ", exception)
                        }
                }
                println("여기 성공 밖" + planBookList.size.toString())

//
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


//        db!!.collection(user!!.email.toString()).document(title.toString())
//            .get().addOnSuccessListener  { documentSnapshot ->
//                val data = documentSnapshot.toObject<PlanTotalData>()
//                if(data != null) {
//                    planTotalData = documentSnapshot.toObject<PlanTotalData>()!!
//                    dayList = planTotalData.dayList
//                }
//            }
//
//        binding.plantripRv.adapter!!.notifyDataSetChanged()

    }


    public fun addPlanAndNotify(data: PlanSectionData) {
        tripPlanList.add(data)
        binding.plantripRv.adapter!!.notifyDataSetChanged()
    }

    fun newInstant(): TravelPlanSectionFragment {
        val args = Bundle()
        val frag = TravelPlanSectionFragment()
        frag.arguments = args
        return frag
    }
}
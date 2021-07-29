package com.hansung.traveldiary.src.travel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.DialogBtmPlanlistBinding
import com.hansung.traveldiary.src.travel.adapter.TravelListAdapter

class PlanlistBottomDialog : BottomSheetDialogFragment() {
    private lateinit var binding : DialogBtmPlanlistBinding
    var regionList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogBtmPlanlistBinding.inflate(inflater, container, false)

        initRegion()

        binding.travellistRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = TravelListAdapter(regionList)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(sheet!!)
        behavior.state = BottomSheetBehavior.STATE_DRAGGING
    }

    fun initRegion(){
        regionList.add("서울")
        regionList.add("경기")
        regionList.add("대구")
        regionList.add("인천")
        regionList.add("부산")
        regionList.add("경남")
        regionList.add("경북")
        regionList.add("충남")
        regionList.add("강원")
        regionList.add("대전")
        regionList.add("충북")
        regionList.add("광주")
        regionList.add("울산")
        regionList.add("전북")
        regionList.add("전남")
        regionList.add("제주")
    }
}
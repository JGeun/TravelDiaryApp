package com.hansung.traveldiary.src.travel.AddBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hansung.traveldiary.databinding.DialogBtmSelectAreaBinding

class SelectAreaBtmDialog() : BottomSheetDialogFragment() {
    private lateinit var binding : DialogBtmSelectAreaBinding
    var regionList = ArrayList<String>()
    private val areaViewModel : AreaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogBtmSelectAreaBinding.inflate(inflater, container, false)

        initRegion()

        binding.travellistRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = SelectAreaAdapter(regionList, areaViewModel, this@SelectAreaBtmDialog)
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
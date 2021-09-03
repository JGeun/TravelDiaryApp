package com.hansung.traveldiary.src.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hansung.traveldiary.databinding.DialogBtmSelectAreaBinding
import com.hansung.traveldiary.src.travel.AddBook.AreaViewModel
import com.hansung.traveldiary.src.travel.AddBook.SelectAreaAdapter

class AirPortBtmDialog (val isDestination:Boolean) : BottomSheetDialogFragment() {
    private lateinit var binding : DialogBtmSelectAreaBinding
    var regionList = ArrayList<String>()

    private val airportViewModel : AirportViewModel by activityViewModels()

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
            adapter = AirPortAreaAdapter(regionList, airportViewModel, this@AirPortBtmDialog,isDestination)
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
        regionList.add("제주")
        regionList.add("부산")
        regionList.add("광주")
        regionList.add("청주")
        regionList.add("여수")

    }
}
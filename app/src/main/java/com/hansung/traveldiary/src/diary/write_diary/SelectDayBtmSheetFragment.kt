package com.hansung.traveldiary.src.diary.write_diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hansung.traveldiary.databinding.BottomSheetDialogFragmentBinding

class SelectDayBtmSheetFragment(val size: Int) : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetDialogFragmentBinding
    var dayList = ArrayList<String>()
    private val selectDayViewModel : SelectDayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetDialogFragmentBinding.inflate(inflater, container, false)
        initRegion()

        binding.daylistRv.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = SelectDayAdapter(dayList, selectDayViewModel, this@SelectDayBtmSheetFragment)
        }
        //날짜 길이 받기
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(sheet!!)
        behavior.state = BottomSheetBehavior.STATE_DRAGGING
    }

    private fun initRegion(){
        for(i in 1..size){
            dayList.add("${i}일차 일기")
        }
    }
}



package com.hansung.traveldiary.src.travel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.databinding.FragmentTravelDiarySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.travel.adapter.DiarySectionAdapter

class TravelDiarySectionFragment : Fragment() {
    private lateinit var binding : FragmentTravelDiarySectionBinding

    private val diarySectionAdapter = DiarySectionAdapter(MainActivity.userDiaryArray)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        println("Create!!!!!!!!")
        println("")
        binding = FragmentTravelDiarySectionBinding.inflate(inflater, container, false)

        if(MainActivity.userDiaryArray.size == 0){
            binding.diarySectionNoPlan.isVisible = true
            binding.diarySectionRecyclerView.isVisible = false
        }else{
            binding.diarySectionNoPlan.isVisible = false
            binding.diarySectionRecyclerView.isVisible = true
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        println("TravelDiarySection Start!!!")
        MainActivity.userDiaryArray.sortBy { it.baseData.startDate }
        binding.diarySectionRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = diarySectionAdapter
        }
        diarySectionAdapter.notifyDataSetChanged()
    }

    fun newInstant() : TravelDiarySectionFragment
    {
        val args = Bundle()
        val frag = TravelDiarySectionFragment()
        frag.arguments = args
        return frag
    }
}

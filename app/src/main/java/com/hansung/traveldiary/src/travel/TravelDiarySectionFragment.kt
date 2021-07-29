package com.hansung.traveldiary.src.travel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.databinding.FragmentLasttripBinding
import com.hansung.traveldiary.src.travel.adapter.TravelDiarySectionAdapter

class TravelDiarySectionFragment : Fragment() {
    private lateinit var binding : FragmentLasttripBinding
//    private val lastTripList = ArrayList<LastTripData>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLasttripBinding.inflate(inflater, container, false)
        binding.lasttripRv.adapter?.notifyDataSetChanged()

        binding.lasttripRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = TravelDiarySectionAdapter(lastTripList)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.lasttripRv.adapter!!.notifyDataSetChanged()
    }

    fun newInstant() : TravelDiarySectionFragment
    {
        val args = Bundle()
        val frag = TravelDiarySectionFragment()
        frag.arguments = args
        return frag
    }
}

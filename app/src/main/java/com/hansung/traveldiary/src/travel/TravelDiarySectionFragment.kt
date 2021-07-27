package com.hansung.traveldiary.src.travel

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentLasttripBinding
import com.hansung.traveldiary.src.travel.adapter.SendTripAdapter

class TravelDiarySectionFragment : Fragment() {
    private lateinit var binding : FragmentLasttripBinding
//    private val lastTripList = ArrayList<LastTripData>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLasttripBinding.inflate(inflater, container, false)

//        this.context?.let {
//            ResourcesCompat.getDrawable(
//                it.resources, R.drawable.ig_home_gangneung, null
//            )?.let {
//                LastTripData(
//                    it, "부산", "#해운대")
//            }?.let {
//                lastTripList.add(
//                    it
//                )
//            }
//        }
        binding.lasttripRv.adapter?.notifyDataSetChanged()

        binding.lasttripRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = SendTripAdapter(lastTripList)
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

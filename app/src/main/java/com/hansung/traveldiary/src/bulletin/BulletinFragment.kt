package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMainBinding.inflate
import com.hansung.traveldiary.databinding.FragmentBulletinBinding
import com.hansung.traveldiary.databinding.FragmentHomeBinding

class BulletinFragment : Fragment(){
    private lateinit var binding : FragmentBulletinBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBulletinBinding.inflate(inflater, container, false)

        binding.FAbtn.setOnClickListener {

        }
        binding.rv.setHasFixedSize(true)
        val posList=arrayListOf(
            PosData(R.drawable.gwangwhamun,"이번 여름을 맞아 부산에 ........."),
            PosData(R.drawable.gwangwhamun,"이번 여름을 맞아 부산에 ........."),
            PosData(R.drawable.gwangwhamun,"이번 여름을 맞아 부산에 .........")
        )
        val adapter= CustomAdapter(posList)
        binding.rv.adapter=adapter
        binding.rv.layoutManager= LinearLayoutManager(context)
        return binding.root
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {

        return super.onContextItemSelected(item)
    }
}
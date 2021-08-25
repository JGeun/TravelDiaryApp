package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.databinding.FragmentBulletinBinding
import com.hansung.traveldiary.src.BulletinData
import com.hansung.traveldiary.src.MainActivity

class BulletinFragment : Fragment(){

    private lateinit var binding : FragmentBulletinBinding
    private val bulletinDiaryArray = ArrayList<BulletinData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBulletinBinding.inflate(inflater, container, false)

        MainActivity.bulletinDiaryArray.sortedBy{it.userDiaryData.baseData.uploadDate}

        binding.bulletinRecyclerView.apply{
            setHasFixedSize(true)
            adapter = BulletinAdapter()
            layoutManager = LinearLayoutManager(context)
        }

        binding.searchIcon.setOnClickListener{
            val intent= Intent(activity,searchActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.bulletinRecyclerView.adapter!!.notifyDataSetChanged()
    }
}
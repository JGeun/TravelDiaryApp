package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentBulletinBinding

class BulletinFragment : Fragment(){

    private lateinit var binding : FragmentBulletinBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBulletinBinding.inflate(inflater, container, false)

        //액션바

        binding.FAbtn.setOnClickListener {

        }
        binding.rv.setHasFixedSize(true)
        val posList=arrayListOf(
            PosData(R.drawable.gwangwhamun, "이번 여름을 맞아 부산에 ........."),
            PosData(R.drawable.gwangwhamun, "이번 여름을 맞아 부산에 ........."),
            PosData(R.drawable.gwangwhamun, "이번 여름을 맞아 부산에 .........")
        )
        val adapter= CustomAdapter(posList)
        binding.rv.adapter=adapter
        binding.rv.layoutManager= LinearLayoutManager(context)
        return binding.root
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.searchIcon -> println("돋보기")
            R.id.overflow -> println("더보기")
        }
        return true
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {

        return super.onContextItemSelected(item)
    }


}
package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.databinding.FragmentBulletinBinding
import com.hansung.traveldiary.src.MainActivity

class BulletinFragment : Fragment(){

    private lateinit var binding : FragmentBulletinBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBulletinBinding.inflate(inflater, container, false)

        binding.bulletinRecyclerView.apply{
            setHasFixedSize(true)
            adapter = BulletinAdapter()
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }
}
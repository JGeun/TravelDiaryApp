package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.databinding.FragmentUserNameBinding
import com.hansung.traveldiary.src.BulletinData
import com.hansung.traveldiary.src.MainActivity

class UserNameFragment : Fragment() {
    var items: ArrayList<BulletinData> =ArrayList<BulletinData>()
    private val viewModel:SearchWordVIewModel by activityViewModels()
    private lateinit var binding:FragmentUserNameBinding
    private val userNameAdapter = UserNameAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentUserNameBinding.inflate(inflater, container, false)
        var searchWord:String
        val searchArray= BulletinData()


        binding.rv.apply {
            setHasFixedSize(true)
            adapter = userNameAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.searchWord.observe(viewLifecycleOwner){
            userNameAdapter.clear()
            searchWord=viewModel.searchWord.value.toString()
            for(i in 0 until MainActivity.bulletinDiaryArray.size){
                println(MainActivity.bulletinDiaryArray[i].userInfo.nickname+"                   "+searchWord.toString())
                if(MainActivity.bulletinDiaryArray[i].userInfo.nickname.equals(searchWord.toString())){
                    println(MainActivity.bulletinDiaryArray[i].userInfo.nickname)
                    Log.d("체크", MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.title)
                    userNameAdapter.add(MainActivity.bulletinDiaryArray[i])
                }
            }
            binding.rv.adapter!!.notifyDataSetChanged()
        }

        return binding.root
    }
}
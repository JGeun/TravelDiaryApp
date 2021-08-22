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


        viewModel.searchWord.observe(viewLifecycleOwner){
            items.clear()
            Log.d("췤","변경된 값은 ${viewModel.searchWord.value}")
            searchWord=viewModel.searchWord.value.toString()
            println("새롭게 넣은 리스트의 길이"+items.size)
            binding.rv.apply {
                setHasFixedSize(true)
                for(i in 0..MainActivity.bulletinDiaryArray.size-1){
                    println(MainActivity.bulletinDiaryArray[i].userInfo.nickname+"                   "+searchWord.toString())
                    if(MainActivity.bulletinDiaryArray[i].userInfo.nickname.equals(searchWord.toString())){
                        println(MainActivity.bulletinDiaryArray[i].userInfo.nickname)
                        items.add(MainActivity.bulletinDiaryArray[i])
                    }
                }
                println("새롭게 넣은 리스트의 길이       "+items.size)
                println()
                adapter = UserNameAdapter(items)
                layoutManager = LinearLayoutManager(context)

            }

        }
        //binding.rv.isVisible=false

        return binding.root
    }
}
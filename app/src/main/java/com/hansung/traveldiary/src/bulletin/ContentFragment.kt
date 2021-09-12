package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentContentBinding
import com.hansung.traveldiary.databinding.FragmentUserNameBinding
import com.hansung.traveldiary.src.BulletinData
import com.hansung.traveldiary.src.MainActivity

class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding
    var items: ArrayList<BulletinData> =ArrayList<BulletinData>()
    private val viewModel:SearchWordVIewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentContentBinding.inflate(inflater, container, false)
        var searchWord:String
        val searchArray= BulletinData()


        viewModel.searchWord.observe(viewLifecycleOwner){
            items.clear()
            Log.d("췤","변경된 값은 ${viewModel.searchWord.value}")
            searchWord=viewModel.searchWord.value.toString()
            println("contentAdapter 안으로 들어옴")
            println("새롭게 넣은 리스트의 길이"+items.size)
            binding.rv.apply {
                setHasFixedSize(true)
                println("배열의 길이는 "+ (MainActivity.bulletinDiaryArray.size-1).toString())
                for(i in 0..MainActivity.bulletinDiaryArray.size-1){
                    for(j in 0..MainActivity.bulletinDiaryArray[i].userDiaryData.diaryArray.size-1) {
                        if (MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.title.contains(searchWord)) {
                            Log.d("제목",MainActivity.bulletinDiaryArray[i].userDiaryData.baseData.title.toString())
                            items.add(MainActivity.bulletinDiaryArray[i])
                            break
                        }
                    }
                }
                adapter = ContentAdapter(items)
                layoutManager = LinearLayoutManager(context)

            }

        }
        return binding.root
    }

}
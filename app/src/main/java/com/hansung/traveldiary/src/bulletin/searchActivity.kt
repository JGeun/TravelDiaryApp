package com.hansung.traveldiary.src.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat.canScrollHorizontally
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivitySearchBinding

class searchActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val popular_words:ArrayList<String> =  arrayListOf()
        popular_words.add("경상남도 명소")
        popular_words.add("부산 맛집")
        popular_words.add("제주도")
        popular_words.add("당일치기")
        popular_words.add("야경")

        val resent_words:ArrayList<String> =  arrayListOf()
        resent_words.add("물회")
        resent_words.add("부산직구 토킹토스트")
        resent_words.add("부산대")
        binding.rv1.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context).also{
                it.orientation=LinearLayoutManager.HORIZONTAL
            }
            adapter = popularWordsAdapter(popular_words)
        }

        binding.rv2.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = resentWordsAdapter(resent_words)
        }
    }
}
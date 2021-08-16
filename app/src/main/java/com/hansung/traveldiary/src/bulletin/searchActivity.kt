package com.hansung.traveldiary.src.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.view.ViewCompat.canScrollHorizontally
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivitySearchBinding
import com.hansung.traveldiary.src.plan.KakaoSearchKeywordService
import com.hansung.traveldiary.util.StatusBarUtil

class searchActivity : AppCompatActivity() {

    private val binding:ActivitySearchBinding by lazy{
        ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        val popular_words:ArrayList<String> =  arrayListOf()
        popular_words.add("경상남도 명소")
        popular_words.add("부산 맛집")
        popular_words.add("제주도")
        popular_words.add("당일치기")
        popular_words.add("야경")
        popular_words.add("경상남도 명소")

        val resent_words:ArrayList<String> =  arrayListOf()
        resent_words.add("물회")
        resent_words.add("부산직구 토킹토스트")
        resent_words.add("부산대")
        var searchWord:String=""
        //최근 검색 값 넣기


        binding.rv1.apply{
            setHasFixedSize(true)
            val gridLayoutManager=GridLayoutManager(applicationContext,3).apply{

            }
            layoutManager = gridLayoutManager
            adapter = popularWordsAdapter(popular_words)
        }

        binding.rv2.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = resentWordsAdapter(resent_words)
        }

        //백버튼
        binding.etSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    println("키 눌렀다")
                    searchWord=binding.etSearch.text.toString()
                    resent_words.add(searchWord)
                    binding.rv2.apply{
                        layoutManager = LinearLayoutManager(context)
                        adapter = resentWordsAdapter(resent_words)

                    }
                    searchWord=""
                    return true
                }
                return false
            }
        })
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}
package com.hansung.traveldiary.src.plan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ActivitySearchWordResultBinding
import com.hansung.traveldiary.src.plan.adapter.SearchWordResultAdapter
import com.hansung.traveldiary.src.plan.model.KakaoSearchKeywordResponse
import com.hansung.traveldiary.src.plan.model.SearchWordResultInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchWordResultActivity : AppCompatActivity(), KakaoSearchView{
    private val binding by lazy {
        ActivitySearchWordResultBinding.inflate(layoutInflater)
    }
    private var searchWord = ""
    private var result = ArrayList<SearchWordResultInfo>()
    private var is_end = true
    private var page = 2

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        is_end = intent.getBooleanExtra("is_end", true)

        binding.srIvBack.setOnClickListener {
            finish()
        }

        println("is_end: " + is_end)

        searchWord = intent.getStringExtra("word").toString()
        binding.srTvWord.text = searchWord

        result = intent.getSerializableExtra("result") as ArrayList<SearchWordResultInfo>

        binding.srRvResult.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchWordResultActivity)
            adapter = SearchWordResultAdapter(result)
        }

        binding.srRvResult.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                println("스크롤~")
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                if (!is_end && !binding.srRvResult.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    println("스크롤 도착")
                    KakaoSearchKeywordService(this@SearchWordResultActivity).tryGetKeyWordSearchInfo(
                        searchWord, page
                    )
                }
            }
        })
    }


    override fun onGetKeywordSearchSuccess(response: KakaoSearchKeywordResponse) {
        val searchWordResultList = response.documents
        for (resultContent in searchWordResultList) {
            result.add(
                SearchWordResultInfo(
                    resultContent.place_name, resultContent.address_name, resultContent.category_group_name
                )
            )
        }
        is_end = response.meta.is_end
        if(!is_end)
            page+=1
        binding.srRvResult.adapter!!.notifyDataSetChanged()
    }

    override fun onGetKeywordSearchFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    private fun showCustomToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
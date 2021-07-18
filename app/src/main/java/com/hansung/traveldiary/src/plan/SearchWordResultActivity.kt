package com.hansung.traveldiary.src.plan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.databinding.ActivitySearchWordResultBinding
import com.hansung.traveldiary.src.plan.adapter.SearchWordResultAdapter
import com.hansung.traveldiary.src.plan.model.SearchWordResultInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchWordResultActivity : AppCompatActivity(){
    private val binding by lazy {
        ActivitySearchWordResultBinding.inflate(layoutInflater)
    }
    private var searchWord = ""
    private var result = ArrayList<SearchWordResultInfo>()

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.srIvBack.setOnClickListener {
            finish()
        }

        searchWord = intent.getStringExtra("word").toString()
        binding.srTvWord.text = searchWord

        result = intent.getSerializableExtra("result") as ArrayList<SearchWordResultInfo>

        binding.srRvResult.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchWordResultActivity)
            adapter = SearchWordResultAdapter(result)
        }
    }
//
//    override fun onGetMapSearchSuccess(response: MapSearchInfo) {
//        var searchInfoList = ArrayList<SearchInfo>()
//        searchInfoList = response.item
//        binding.srRvResult.apply{
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(this@SearchWordResultActivity)
//            adapter = SearchWordResultAdapter(searchInfoList)
//
//        }
//    }
//
//    override fun onGetMapSearchFailure(message: String) {
//        showCustomToast("오류 : $message")
//    }

    private fun showCustomToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
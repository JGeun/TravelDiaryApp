package com.hansung.traveldiary.src.plan

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ActivitySearchWordResultBinding
import com.hansung.traveldiary.src.plan.adapter.SearchWordResultAdapter
import com.hansung.traveldiary.src.plan.model.KakaoSearchKeywordInfo
import com.hansung.traveldiary.src.plan.model.KakaoSearchKeywordResponse
import com.hansung.traveldiary.src.plan.model.SearchWordResultInfo
import com.hansung.traveldiary.util.LoadingDialog
import com.hansung.traveldiary.util.StatusBarUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchWordResultActivity : AppCompatActivity(), KakaoSearchView{
    private val binding by lazy {
        ActivitySearchWordResultBinding.inflate(layoutInflater)
    }
    private lateinit var searchWordResultTask: ActivityResultLauncher<Intent>
    private var categoryGCeMap : HashMap<String, String> = HashMap()
    private var searchWord = ""
    private var searchResult = ArrayList<SearchWordResultInfo>()

    private var is_end = true
    private var page = 2
    lateinit var mLoadingDialog: LoadingDialog

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        initGCMap()

        is_end = intent.getBooleanExtra("is_end", true)

        binding.srIvBack.setOnClickListener {
            finish()
        }

        searchWord = intent.getStringExtra("word").toString()

        searchResult = intent.getSerializableExtra("result") as ArrayList<SearchWordResultInfo>

        binding.srRvResult.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchWordResultActivity)
            //?????????
            adapter = SearchWordResultAdapter(searchResult, categoryGCeMap)
        }

        binding.srTvWord.setText(searchWord)
        binding.srTvWord.setOnKeyListener(object : View.OnKeyListener {

            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchWord = binding.srTvWord.text.toString()
                    searchResult.clear()
                    KakaoSearchKeywordService(this@SearchWordResultActivity).tryGetKeyWordSearchInfo(searchWord, 1)
                    return true
                }
                return false
            }
        })



        binding.srRvResult.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                println("?????????~")
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                if (!is_end && !binding.srRvResult.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    println("????????? ??????")
                    KakaoSearchKeywordService(this@SearchWordResultActivity).tryGetKeyWordSearchInfo(
                        searchWord, page
                    )
                }

            }
        })
    }

    fun initGCMap(){
        categoryGCeMap.put("MT1", "????????????")
        categoryGCeMap.put("CS2", "?????????")
        categoryGCeMap.put("PS3", "????????????")
        categoryGCeMap.put("SC4", "??????")
        categoryGCeMap.put("AC5", "??????")
        categoryGCeMap.put("PK6", "?????????")
        categoryGCeMap.put("OL7", "?????????")
        categoryGCeMap.put("SW8", "????????????")
        categoryGCeMap.put("BK9", "??????")
        categoryGCeMap.put("AG2", "????????????")
        categoryGCeMap.put("PO3", "????????????")
        categoryGCeMap.put("AT4", "????????????")
        categoryGCeMap.put("AD5", "??????")
        categoryGCeMap.put("FD6", "?????????")
        categoryGCeMap.put("CE7", "??????")
        categoryGCeMap.put("HP8", "??????")
        categoryGCeMap.put("PM9", "??????")
    }

    override fun onGetKeywordSearchSuccess(response: KakaoSearchKeywordResponse?) {
        if(response != null){
            val searchWordResultList = response.documents
            Log.d("??????","?????? Page ??? : ${page} total: ${response.meta.pageable_count} is_end: ${response.meta.is_end}")
            for (result in searchWordResultList) {
                searchResult.add(SearchWordResultInfo(result.place_name, result.address_name,result.category_group_code,
                    result.category_name, result.road_address_name, result.phone, result.place_url, result.x, result.y))
            }
            binding.srRvResult.apply{
                setHasFixedSize(true)
                adapter = SearchWordResultAdapter(searchResult, categoryGCeMap)
            }
            searchWord=""
        }else{
            showCustomToast("????????? ????????? ?????????.")
        }
    }

    override fun onGetKeywordSearchFailure(message: String) {
        showCustomToast("?????? : $message")
    }

    private fun showCustomToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }
}
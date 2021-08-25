package com.hansung.traveldiary.src.bulletin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.view.ViewCompat.canScrollHorizontally
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivitySearchBinding
import com.hansung.traveldiary.src.plan.KakaoSearchKeywordService
import com.hansung.traveldiary.util.StatusBarUtil

class searchActivity : AppCompatActivity() {
    private val binding:ActivitySearchBinding by lazy{
        ActivitySearchBinding.inflate(layoutInflater)
    }
    private val viewModel:SearchWordVIewModel by viewModels()
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        val spinnerArray=arrayListOf("인스타", "유저")
        var searchWord:String?=null
        binding.viewpager.adapter = SearchViewPagerAdapter(this)

        binding.editText2.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event!!.action == KeyEvent.ACTION_DOWN) || keyCode == KeyEvent.KEYCODE_ENTER ||keyCode==KeyEvent.KEYCODE_DPAD_DOWN) {
                    viewModel.searchWord.value=binding.editText2.text.toString()
                    binding.editText2.text= null
                }
                return false
            }
        })

        TabLayoutMediator(binding.tabLayout,binding.viewpager){tab, position->
            when(position){
                0->tab.text="인스타"
                1->tab.text="게시글"
                2->tab.text="유저"
            }
        }.attach()

        binding.ivBack.setOnClickListener {
            finish()
        }

    }
}
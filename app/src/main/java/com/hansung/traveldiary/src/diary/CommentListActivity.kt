package com.hansung.traveldiary.src.diary

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityCommentListBinding
import com.hansung.traveldiary.src.plan.KakaoSearchKeywordService

class CommentListActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityCommentListBinding.inflate(layoutInflater)
    }
    private val commentsAdapter=CommentsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }
        binding.ivX.setOnClickListener {
            finish()
        }
        window.statusBarColor = Color.parseColor("#2C2C2C")
        binding.writeComment.setOnKeyListener(object : View.OnKeyListener {

            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {

                if ((event!!.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    println("클릭을 했따")
                    return true
                }
                return false
            }
        })
    }
}
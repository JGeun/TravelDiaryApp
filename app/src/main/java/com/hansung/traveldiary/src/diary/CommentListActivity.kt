package com.hansung.traveldiary.src.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityCommentListBinding

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
    }
}
package com.hansung.traveldiary.src.diary

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityCommentListBinding
import com.hansung.traveldiary.src.CommentsData
import com.hansung.traveldiary.src.CommentsFolder
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.plan.diary
import java.text.SimpleDateFormat
import java.util.*


class CommentListActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCommentListBinding.inflate(layoutInflater)
    }
    private var index = 0
    private var myDiary = false
    private var diaryComments = CommentsFolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = ResourcesCompat.getColor(
            resources,
            R.color.comments_status_color,
            null
        )

        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        index = intent.getIntExtra("index", 0)
        Log.d("체크", "CommentsList 받은 index: ${index}")
        myDiary = intent.getBooleanExtra("myDiary", false)

        if (myDiary){
            diaryComments = MainActivity.userDiaryArray[index].baseData.comments
            Log.d("체크", "CommentsList 제 다이어리입니다")
            Log.d("체크", "idx: ${MainActivity.userDiaryArray[index].baseData.idx}}")
            Log.d("체크", "idx: ${MainActivity.userDiaryArray[index].baseData.userEmail}}")
        } else {
            diaryComments = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.comments
            Log.d("체크", "CommentsList Bulletin 다이어리입니다")
            Log.d("체크", "idx: ${MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.idx}}")
            Log.d("체크", "idx: ${MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.userEmail}}")
        }


        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CommentsAdapter(diaryComments, index)
        }

        binding.ivX.setOnClickListener {
            finish()
        }

        binding.commentsIvSend.setOnClickListener {
            val text = binding.writeComment.text.toString()
            var diaryUserEmail = ""
            val userEmail = user!!.email.toString()
            if (text == "") showCustomToast("댓글을 입력해주세요")
            else {
                var idx = 0L

                var date =
                    SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.getDefault()).format(Date())

                if (myDiary) {
                    Log.d("체크", "MyDiary")
                    idx = MainActivity.userDiaryArray[index].baseData.idx
                    diaryUserEmail = MainActivity.userDiaryArray[index].baseData.userEmail
                    MainActivity.userDiaryArray[index].baseData.comments.commentsFolder.add(
                        CommentsData(userEmail, text, date)
                    )

                    db.collection("Diary").document(diaryUserEmail)
                        .collection("DiaryData").document(idx.toString())
                        .set(MainActivity.userDiaryArray[index].baseData).addOnSuccessListener {
                            Log.d("체크", "성공")
                            binding.recyclerView.adapter!!.notifyDataSetChanged()
                            binding.writeComment.setText("")
                            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        }
                } else {
                    Log.d("체크", "Bulletin")
                    idx = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.idx
                    diaryUserEmail = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.userEmail
                    MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.comments.commentsFolder.add(
                        CommentsData(userEmail, text, date)
                    )
                    db.collection("Diary").document(diaryUserEmail)
                        .collection("DiaryData").document(idx.toString())
                        .set(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData).addOnSuccessListener {
                            Log.d("체크", "성공")
                            binding.recyclerView.adapter!!.notifyDataSetChanged()
                            binding.writeComment.setText("")
                            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("체크", "CommentList Activity Start")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
package com.hansung.traveldiary.src.diary

import android.os.Bundle
import android.util.Log
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
import com.hansung.traveldiary.src.MainActivity
import java.text.SimpleDateFormat
import java.util.*


class CommentListActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityCommentListBinding.inflate(layoutInflater)
    }
    private var index = 0
    private var myDiary = false

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
        myDiary = intent.getBooleanExtra("myDiary", false)

        if(myDiary){
            binding.recyclerView.apply{
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = CommentsAdapter(MainActivity.userDiaryArray[index].baseData.comments)
            }
        }else{
            binding.recyclerView.apply{
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = CommentsAdapter(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.comments)
            }
        }


        binding.ivX.setOnClickListener {
            finish()
        }

        binding.commentsIvSend.setOnClickListener{
            val text = binding.writeComment.text.toString()
            var diaryUserEmail = ""
            val userEmail = user!!.email.toString()
            if(text == "") showCustomToast("댓글을 입력해주세요")
            else{
                var idx = 0L

                var date = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.getDefault()).format(Date())

                if(myDiary){
                    Log.d("체크", "MyDiary")
                    idx = MainActivity.userDiaryArray[index].baseData.idx
                    diaryUserEmail = MainActivity.userDiaryArray[index].baseData.userEmail
                    MainActivity.userDiaryArray[index].baseData.comments.commentsFolder.add(CommentsData(userEmail, text, date))
                }else{
                    Log.d("체크", "Bulletin")
                    idx = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.idx
                    diaryUserEmail = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.userEmail
                    MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.comments.commentsFolder.add(CommentsData(userEmail, text, date))
                }

                db.collection("Diary").document(diaryUserEmail)
                    .collection("DiaryData").document(idx.toString())
                    .set(MainActivity.userDiaryArray[index].baseData).addOnSuccessListener {
                        Log.d("체크", "성공")
                        binding.recyclerView.adapter!!.notifyDataSetChanged()
                    }

                binding.writeComment.setText("")
            }
        }

//        binding.writeComment.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
//                if ((event!!.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
//                    println("클릭을 했따")
//                    return true
//                }
//                return false
//            }
//        })
    }

    private fun showCustomToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
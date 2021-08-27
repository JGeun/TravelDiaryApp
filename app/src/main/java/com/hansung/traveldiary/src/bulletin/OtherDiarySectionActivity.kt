package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityOtherDiarySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.diary.DiaryDayViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.src.DiaryBaseData
import com.hansung.traveldiary.src.IdxList
import com.hansung.traveldiary.src.MainActivity.Companion.userDiaryArray
import com.hansung.traveldiary.src.diary.CommentListActivity
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionAdapter
import com.hansung.traveldiary.util.StatusBarUtil

class OtherDiarySectionActivity() : AppCompatActivity() {
    private val binding by lazy {
        ActivityOtherDiarySectionBinding.inflate(layoutInflater)
    }
    private var DBD = ArrayList<DiaryBaseData>()
    private val viewModel: DiaryDayViewModel by viewModels()
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private var chk_like = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = Firebase.auth.currentUser
        db = Firebase.firestore

        StatusBarUtil.setStatusBarColor(
            this,
            StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR
        )

        binding.dsIvBack.setOnClickListener {
            finish()
        }
        val index = intent.getIntExtra("index", 0)
        val email = intent.getStringExtra("email")
        val data = MainActivity.bulletinDiaryArray[index].userDiaryData
        var myDiary=false
        if(user!!.email.toString().equals(email)){
            myDiary=true
        }
//        getDiary(email.toString(),index)
        binding.countLikes.text = data.baseData.like.likeUserFolder.size.toString()
        binding.countComments.text = data.baseData.comments.commentsFolder.size.toString()


        val likeRef = db!!.collection("Diary").document(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.userEmail)
            .collection("DiaryData").document(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.idx.toString())
        binding.ivLike.setOnClickListener {
            if(!chk_like){
                binding.ivLike.setImageResource(R.drawable.asset7)
                chk_like=true
                MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.add(user!!.email.toString())
                likeRef.set(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData)
                binding.countLikes.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
            }else{
                binding.ivLike.setImageResource(R.drawable.emptyheart)
                chk_like=false
                MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.remove(user!!.email.toString())
                likeRef.set(MainActivity.bulletinDiaryArray[index].userDiaryData.baseData)
                binding.countLikes.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.like.likeUserFolder.size.toString()
            }
        }

        Log.d("이메일", email.toString())

        binding.dsTitle.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.title
        binding.dsIvBack.setOnClickListener {
            finish()
        }
        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter = OtherUserDiarySectionAdapter(index)
            layoutManager = LinearLayoutManager(this@OtherDiarySectionActivity)
        }

        binding.commentLayout.setOnClickListener {
            val intent =Intent(this, CommentListActivity::class.java)
            intent.putExtra("index",index)
            //intent.putExtra("myDiary",myDiary)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.dsRecyclerview.adapter!!.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0, 0)
    }
}

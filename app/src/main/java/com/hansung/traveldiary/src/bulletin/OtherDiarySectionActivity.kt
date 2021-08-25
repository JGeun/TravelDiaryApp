package com.hansung.traveldiary.src.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMyDiaryDaySectionBinding
import com.hansung.traveldiary.databinding.ActivityOtherDiarySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.diary.DiaryDayViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.src.diary.MyDiaryDaySectionAdapter
import com.hansung.traveldiary.util.StatusBarUtil

class OtherDiarySectionActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityOtherDiarySectionBinding.inflate(layoutInflater)
    }
    private val viewModel: DiaryDayViewModel by viewModels()
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private var chk_like=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        println("여기 들어옴")
        StatusBarUtil.setStatusBarColor(
            this,
            StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR
        )

        val showDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_find_black, null)
        val writeDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_edit, null)
        binding.dsIvBack.setOnClickListener {
            finish()
            //overridePendingTransition(0, 0)
        }

        val index = intent.getIntExtra("index", 0)

        binding.dsTitle.text = MainActivity.userDiaryArray[index].baseData.title
        binding.dsIvBack.setOnClickListener{
            finish()
        }
        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter = OtherUserDiarySectionAdapter(index, viewModel)
            layoutManager = LinearLayoutManager(this@OtherDiarySectionActivity)
        }

        val likeRef = db!!.collection("Diary").document(user!!.email.toString())
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
package com.hansung.traveldiary.src.home

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentHomeBinding
import com.hansung.traveldiary.src.home.adapter.HomeBulletinAdapter
import com.hansung.traveldiary.src.home.adapter.RecommandAdapter
import com.readystatesoftware.systembartint.SystemBarTintManager

data class RecommandLocationData(val image : Drawable, val name : String)
data class HomeBulletinData(val image : Drawable, val title : String, val contents: String)

class HomeFragment : Fragment(){
    private lateinit var binding : FragmentHomeBinding
    private val recommandLocationList = ArrayList<RecommandLocationData>()
    private val homeBulletinList = ArrayList<HomeBulletinData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        activity?.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        activity?.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN }

//        // create our manager instance after the content view is set
//        val tintManager = SystemBarTintManager(activity)
//        // enable status bar tint\
//        tintManager.isStatusBarTintEnabled = true
//        // enable navigation bar tint
//        tintManager.setNavigationBarTintEnabled(true)
//
//        // StatusBar를 20% 투명도를 가지게 합니다.
//        tintManager.setTintColor(Color.parseColor("#00000000"));

        initRecommandLocationList()
        initBulletinList()

        binding.homeRvRecommand.apply{
            adapter = RecommandAdapter(recommandLocationList)
            setHasFixedSize(false)
            val horizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = horizontalManager
        }

        binding.homeRvBulletin.apply{
            adapter = HomeBulletinAdapter(homeBulletinList)
            setHasFixedSize(false)
            val horizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = horizontalManager
        }
        return binding.root
    }

    private fun initRecommandLocationList(){
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul, null)!!, "서울"))
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)!!, "부산"))
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_gangneung, null)!!, "강릉"))
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul_nearby, null)!!, "서울근교"))
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)!!, "대전"))
    }

    private fun initBulletinList(){
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul, null)!!, "서울 2박3일 여행~", "친구들과 여행을 다녀왔는데 너무 좋았어요."))
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)!!, "부산 캡슐열차타러 !!", "캡슐열차 강추 무조건!!"))
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_gangneung, null)!!, "제주도 미쳤다", "힐링하려는 분들 제 글보고 가보세요"))
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul_nearby, null)!!, "서울근교 데이트코스가 필요하다면?", "남친이랑 다녀왔는데.."))
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)!!, "1박2일 가볍게 다녀왔습니다", "강릉바다 보고싶어서 다녀왔는데 아쉬웠어요"))
    }
}
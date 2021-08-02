package com.hansung.traveldiary.src.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentHomeBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.home.adapter.*
import com.hansung.traveldiary.src.home.model.WeatherInfo
import com.hansung.traveldiary.src.home.weather.WeatherActivity
import kotlin.math.ceil

data class  TipData(var image : Drawable, var content: String)
data class RecommandLocationData(val image : Drawable, val name : String)
data class HomeBulletinData(val image : Drawable, val title : String, val contents: String)

class HomeFragment : Fragment(), HomeView{
    private lateinit var binding : FragmentHomeBinding
    private val recommandLocationList = ArrayList<RecommandLocationData>()
    private val homeBulletinList = ArrayList<HomeBulletinData>()
    private val homeSaleList = ArrayList<SaleData>()
    private val homeTipList = ArrayList<TipData>()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Home Create")
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        activity?.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        activity?.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN }

        if(MainActivity.firstStart){
            WeatherService(this).tryGetWeatherInfo()
            MainActivity.firstStart = false
        }else{
            binding.homeWeatherTemp.text = MainActivity.tempText
            binding.homeWeatherIcon.setImageDrawable(MainActivity.weatherIcon)
            binding.homeWeatherText.text= MainActivity.weatherMain
        }

        binding.homeClWeather.setOnClickListener{
            startActivity(Intent(context, WeatherActivity::class.java))
        }
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
        initSaleList()
        initTipList()
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

        binding.homeRvSale.apply{
            adapter = HomeSaleAdapter(homeSaleList)
            setHasFixedSize(false)
            val horizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = horizontalManager
        }

        binding.homeRvTip.apply{
            adapter = HomeTipAdapter(homeTipList)
            setHasFixedSize(false)
            val horizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = horizontalManager
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        println("Home Start")
    }

    private fun initRecommandLocationList(){
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.img_seoul, null)!!, "서울"))
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.img_seoul_cup, null)!!, "부산"))
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.img_beach, null)!!, "강릉"))
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.img_jeju_island, null)!!, "제주"))
        recommandLocationList.add(RecommandLocationData(ResourcesCompat.getDrawable(resources, R.drawable.img_seoul_cup, null)!!, "서울 근교"))
    }

    private fun initBulletinList(){
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul, null)!!, "서울 2박3일 여행~", "친구들과 여행을 다녀왔는데 너무 좋았어요."))
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)!!, "부산 캡슐열차타러 !!", "캡슐열차 강추 무조건!!"))
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_gangneung, null)!!, "제주도 미쳤다", "힐링하려는 분들 제 글보고 가보세요"))
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul_nearby, null)!!, "서울근교 데이트코스가 필요하다면?", "남친이랑 다녀왔는데.."))
        homeBulletinList.add(HomeBulletinData(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)!!, "1박2일 가볍게 다녀왔습니다", "강릉바다 보고싶어서 다녀왔는데 아쉬웠어요"))
    }

    private fun initSaleList(){
        homeSaleList.add(SaleData(ResourcesCompat.getDrawable(resources, R.drawable.img_sumset, null)!!, "제주", "16,200원"))
        homeSaleList.add(SaleData(ResourcesCompat.getDrawable(resources, R.drawable.img_sumset, null)!!, "제주", "16,200원"))
        homeSaleList.add(SaleData(ResourcesCompat.getDrawable(resources, R.drawable.img_sumset, null)!!, "제주", "16,200원"))
        homeSaleList.add(SaleData(ResourcesCompat.getDrawable(resources, R.drawable.img_sumset, null)!!, "제주", "16,200원"))
        homeSaleList.add(SaleData(ResourcesCompat.getDrawable(resources, R.drawable.img_sumset, null)!!, "제주", "16,200원"))
        homeSaleList.add(SaleData(ResourcesCompat.getDrawable(resources, R.drawable.img_sumset, null)!!, "제주", "16,200원"))
        homeSaleList.add(SaleData(ResourcesCompat.getDrawable(resources, R.drawable.img_sumset, null)!!, "제주", "16,200원"))
    }

    private fun initTipList(){
        homeTipList.add(TipData(ResourcesCompat.getDrawable(resources, R.drawable.img_tip1, null)!!, "공항 이용 팁"))
        homeTipList.add(TipData(ResourcesCompat.getDrawable(resources, R.drawable.img_tip2, null)!!, "내일로 기차 여행"))
        homeTipList.add(TipData(ResourcesCompat.getDrawable(resources, R.drawable.img_tip3, null)!!, "짐가방 체크리스트"))
        homeTipList.add(TipData(ResourcesCompat.getDrawable(resources, R.drawable.img_tip4, null)!!, "지역 관광 사이트"))
        homeTipList.add(TipData(ResourcesCompat.getDrawable(resources, R.drawable.img_tip5, null)!!, "캠핑 준비하는 방법"))
        homeTipList.add(TipData(ResourcesCompat.getDrawable(resources, R.drawable.img_tip6, null)!!, "별자리 구분법"))
    }

    override fun onGetWeatherInfoSuccess(response: WeatherInfo) {
        val temp = ceil(response.current.temp)
        MainActivity.weatherId = response.current.weather[0].id.toString()
        MainActivity.tempText = temp.toInt().toString() + "°C"
        binding.homeWeatherTemp.text = MainActivity.tempText
        if(MainActivity.weatherId.substring(0,1).equals("2")){
            MainActivity.weatherMain = "뇌우"
            MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_thunderstorm_white, null)!!
        }else if(MainActivity.weatherId.substring(0,1).equals("3")){
            MainActivity.weatherMain = "이슬비"
            MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_drizzling_white, null)!!
        }else if(MainActivity.weatherId.substring(0,1).equals("5")){
            MainActivity.weatherMain = "비"
            MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_rain_white, null)!!
        }else if(MainActivity.weatherId.substring(0,1).equals("6")){
            MainActivity.weatherMain = "눈"
            MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_snow_white, null)!!
        }else if(MainActivity.weatherId.equals("800")){
            MainActivity.weatherMain = "맑음"
            MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_sunny_white, null)!!
        }else if(MainActivity.weatherId.substring(0,1).equals("8")){
            MainActivity.weatherMain = "구름"
            MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_cloudy_white, null)!!
        }else{
            if(MainActivity.weatherId.equals("771") || MainActivity.weatherId.equals("781")){
                MainActivity.weatherMain = "돌풍"
                MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_windy_white, null)!!
            }else if(MainActivity.weatherId.equals("731") || MainActivity.weatherId.equals("751") || MainActivity.weatherId.equals("761") || MainActivity.weatherId.equals("762")){
                MainActivity.weatherMain = "먼지"
                MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_dust_white, null)!!
            }else if(MainActivity.weatherId.substring(0,1).equals("7")){
                MainActivity.weatherMain = "안개"
                MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_fog_white, null)!!
            }else{
                MainActivity.weatherMain = "맑음"
                MainActivity.weatherIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_sunny_white, null)!!
            }
        }

        binding.homeWeatherIcon.setImageDrawable(MainActivity.weatherIcon)
        binding.homeWeatherText.text= MainActivity.weatherMain
    }

    override fun onGetUserInfoFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    private fun showCustomToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
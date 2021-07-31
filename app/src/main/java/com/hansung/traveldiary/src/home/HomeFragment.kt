package com.hansung.traveldiary.src.home

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
import com.hansung.traveldiary.src.home.adapter.*
import com.hansung.traveldiary.src.home.model.WeatherInfo
import java.lang.Math.*
import kotlin.math.ceil

data class RecommandLocationData(val image : Drawable, val name : String)
data class HomeBulletinData(val image : Drawable, val title : String, val contents: String)

class HomeFragment : Fragment(), HomeView{
    private lateinit var binding : FragmentHomeBinding
    private val recommandLocationList = ArrayList<RecommandLocationData>()
    private val homeBulletinList = ArrayList<HomeBulletinData>()
    private val homeSaleList = ArrayList<SaleData>()
    private val homeCautionList = ArrayList<CautionData>()

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

        WeatherService(this).tryGetWeatherInfo()
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
        initCautionList()
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

        binding.homeRvCaution.apply{
            adapter = HomeCautionAdapter(homeCautionList)
            setHasFixedSize(false)
            val horizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = horizontalManager
        }
        return binding.root
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

    private fun initCautionList(){
        homeCautionList.add(CautionData(ResourcesCompat.getDrawable(resources, R.drawable.ic_book, null)!!, "짐가방 체크리스트"))
        homeCautionList.add(CautionData(ResourcesCompat.getDrawable(resources, R.drawable.ic_redbook, null)!!, "짐가방 체크리스트"))
        homeCautionList.add(CautionData(ResourcesCompat.getDrawable(resources, R.drawable.ic_yellowbook, null)!!, "짐가방 체크리스트"))
        homeCautionList.add(CautionData(ResourcesCompat.getDrawable(resources, R.drawable.ic_graybook, null)!!, "짐가방 체크리스트"))
        homeCautionList.add(CautionData(ResourcesCompat.getDrawable(resources, R.drawable.ic_bluebook, null)!!, "짐가방 체크리스트"))
        homeCautionList.add(CautionData(ResourcesCompat.getDrawable(resources, R.drawable.ic_darkgraybook, null)!!, "짐가방 체크리스트"))
        homeCautionList.add(CautionData(ResourcesCompat.getDrawable(resources, R.drawable.ic_greenbook, null)!!, "짐가방 체크리스트"))
    }

    override fun onGetWeatherInfoSuccess(response: WeatherInfo) {
        val temp = ceil(response.current.temp)
        val weatherId = response.current.weather[0].id.toString()
        var weatherMain = ""
        val tempText = temp.toInt().toString() + "°C"
        binding.homeWeatherTemp.text = tempText
        if(weatherId.substring(0,1).equals("2")){
            weatherMain = "뇌우"
            binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_thunderstorm_white, null))
        }else if(weatherId.substring(0,1).equals("3")){
            weatherMain = "이슬비"
            binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_drizzling_white, null))
        }else if(weatherId.substring(0,1).equals("5")){
            weatherMain = "비"
            binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_rain_white, null))
        }else if(weatherId.substring(0,1).equals("6")){
            weatherMain = "눈"
            binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_snow_white, null))
        }else if(weatherId.equals("800")){
            weatherMain = "맑음"
            binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_sunny_white, null))
        }else if(weatherId.substring(0,1).equals("8")){
            weatherMain = "구름"
            binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_cloudy_white, null))
        }else{
            if(weatherId.equals("771") || weatherId.equals("781")){
                weatherMain = "돌풍"
                binding.homeWeatherText.text= weatherMain
                binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_windy_white, null))
            }else if(weatherId.equals("731") || weatherId.equals("751") || weatherId.equals("761") || weatherId.equals("762")){
                weatherMain = "먼지"
                binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_dust_white, null))
            }else if(weatherId.substring(0,1).equals("7")){
                weatherMain = "안개"
                binding.homeWeatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_fog_white, null))
            }else{
                weatherMain = "null"
            }
        }

        binding.homeWeatherText.text= weatherMain
    }

    override fun onGetUserInfoFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    private fun showCustomToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
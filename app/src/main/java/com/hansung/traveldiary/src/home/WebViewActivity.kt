package com.hansung.traveldiary.src.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityWebViewBinding

class WebViewActivity() : AppCompatActivity() {
    private val binding by lazy{
        ActivityWebViewBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val ToCityId=intent.getStringExtra("ToCityId")
        val FromCityId=intent.getStringExtra("FromCityId")
        val deperatureDay=intent.getStringExtra("deperature").toString().split("-")
        val returnDay=intent.getStringExtra("return").toString().split("-")
        println(deperatureDay+" "+returnDay)
        webViewSetUp(ToCityId!!,FromCityId!!, deperatureDay,returnDay)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    fun webViewSetUp(ToCityId:String, FromCityId:String,deperatureDay:List<String>,returnDay:List<String> ){
        binding.webview.webViewClient= WebViewClient()
        binding.webview.apply {

           loadUrl("https://kr.trip.com/m/flights/flightfirst/?ddate=${deperatureDay.get(0)}-${deperatureDay.get(1)}-${deperatureDay.get(2)}&dcitycode=${ToCityId}&dairportcode=ALL&acitycode=${FromCityId}&aairportcode=ALL&adate=${returnDay.get(0)}-${returnDay.get(1)}-${returnDay.get(2)}&triptype=1&classtype=0&classgroupsearch=true&adult=1&child=0&infant=0&from=flighthome")

            settings.javaScriptEnabled=true
            settings.safeBrowsingEnabled=true


        }
    }
}
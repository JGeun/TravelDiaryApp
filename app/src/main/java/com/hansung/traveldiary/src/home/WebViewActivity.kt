package com.hansung.traveldiary.src.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.hansung.traveldiary.R

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.loadUrl("https://kr.trip.com/flights/shouer-to-jeju/tickets-sel-cju?dcity=sel&acity=cju&ddate=2021-09-01&rdate=2021-09-05&flighttype=rt&class=y&quantity=1&searchboxarg=t")
    }
}
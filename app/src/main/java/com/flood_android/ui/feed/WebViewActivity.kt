package com.flood_android.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.flood_android.R
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
    }

    private fun initView(url: String) {
        wv_news.settings.javaScriptEnabled = true
        wv_news.loadUrl(url)
        wv_news.webChromeClient = WebChromeClient()
        wv_news.webViewClient = WebViewClientClass()
    }

    // 뒤로가기 버튼 이벤트
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_news.canGoBack()) {
            wv_news.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private inner class WebViewClientClass : WebViewClient() {
        //페이지 이동
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.d("check URL", url)
            view.loadUrl(url)
            return true
        }
    }
}

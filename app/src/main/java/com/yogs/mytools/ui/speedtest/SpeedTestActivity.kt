package com.yogs.mytools.ui.speedtest

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import com.yogs.mytools.R
import com.yogs.mytools.data.DataProvider
import com.yogs.mytools.databinding.ActivitySpeedTestBinding
import com.yogs.mytools.util.copyToClipboard
import com.yogs.mytools.util.setUpAppBar

class SpeedTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpeedTestBinding
    private lateinit var webView : WebView
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeedTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = binding.toolbar
        setUpAppBar(toolbar)

        webView = binding.webView
        toolbar.post {
            val toolbarHeight = toolbar.height
            webView.setPadding(0, toolbarHeight, 0, 0)

        }

        showSpeedTest()


    }


    private fun showSpeedTest(){
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = SpeedTestWebViewClient()
        webView.setBackgroundColor(Color.TRANSPARENT)

        val htmlData = DataProvider.getSpeedTestHtml()
        webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
    }


    private inner class SpeedTestWebViewClient : WebViewClient(){
        //handle on click url
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url.toString()

            val disablePrefixResult =  "results"
            val disablePrefixWidget = "widget"
            if(url.contains(disablePrefixResult, ignoreCase = true)){
                copyToClipboard(getString(R.string.copy_url), url, getString(R.string.message_success_copy_to_clipboard, getString(R.string.url)))
                //disable click url has results
                return true
            }else if(url.contains(disablePrefixWidget, ignoreCase = true)){
                //disable click url has widget
                return true
            }else{
                return false
            }
        }



    }
}
package com.yogs.mytools.ui.speedtest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
            val toolbarHeight = toolbar.height - 52
            val layoutParams = webView.layoutParams
            if (layoutParams is ViewGroup.MarginLayoutParams) {
                // 3. Atur topMargin dengan nilai toolbarHeight
                layoutParams.topMargin = toolbarHeight

                // 4. Terapkan kembali LayoutParams yang sudah diubah
                webView.layoutParams = layoutParams
            }

        }

        showSpeedTest()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.speedtest_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_refresh -> {
                webView.reload()
                 true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }



    private fun showSpeedTest(){
        @SuppressLint("SetJavaScriptEnabled")
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

            if(url.contains(getString(R.string.disable_prefix_results), ignoreCase = true)){
                copyToClipboard(getString(R.string.copy_url), url, getString(R.string.message_success_copy_to_clipboard, getString(R.string.url)))
                //disable click url has results
                return true
            }else if(url.contains(getString(R.string.disable_prefix_widget), ignoreCase = true)){
                //disable click url has widget
                return true
            }else{
                return false
            }
        }
    }
}
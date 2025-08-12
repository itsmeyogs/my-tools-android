package com.yogs.mytools.ui.router_manager

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.R.*
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivityRouterManagerBinding
import com.yogs.mytools.util.setUpAppBar
import com.yogs.mytools.viewmodel.RouterManagerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouterManagerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRouterManagerBinding
    private val viewModel: RouterManagerViewModel by viewModel<RouterManagerViewModel>()
    private lateinit var webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouterManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAppBar(binding.toolbar)
        webView = binding.webView

        checkAndRequestPermissionLocation()
        observeConnectionStatus()
    }


    private fun observeConnectionStatus(){
        viewModel.statusConnection.observe(this){data->
            binding.apply {
                tvWifiStatus.text = getString(R.string.wifi_status, getStringWifiStatus(data.isConnected))
                tvWifiSsid.text = getString(R.string.wifi_ssid, data.ssid)
                tvIpAddress.text = getString(R.string.ip_address, data.ipAddress)
                tvDefaultGateway.text = getString(R.string.default_gateway, data.defaultGateway)

                if(data.isConnected){
                    lifecycleScope.launch {
                        loadingInitWebView.visibility = View.VISIBLE
                        delay(3000)
                        loadingInitWebView.visibility = View.GONE
                        cardConnectionStatus.visibility = View.GONE
                        showWebView(data.defaultGateway)
                    }
                }
            }
        }
    }


    private fun showWebView(gateway : String){
        changeAppBarStyle()
        binding.webView.apply {
            @SuppressLint("SetJavaScriptEnabled")
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.loadingPageWebView.visibility = View.VISIBLE
                    binding.webView.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.loadingPageWebView.visibility = View.GONE
                }
            }
            loadUrl("http://$gateway")

        }


    }

    private fun changeAppBarStyle(){
        val appBarLayout = binding.appBarLayout
        val typedValue = TypedValue()
        val theme = appBarLayout.context.theme
        theme.resolveAttribute(attr.colorPrimary, typedValue, true)
        appBarLayout.setBackgroundColor(typedValue.data)
    }




    private fun getStringWifiStatus(value: Boolean):String{
        return if(value) getString(R.string.wifi_status_connected) else getString(R.string.wifi_status_not_connected)
    }

    private fun checkAndRequestPermissionLocation(){
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(permission), LOCATION_PERMISSION_REQUEST_CODE)
        }else{
            viewModel.getConnectionStatus()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            viewModel.getConnectionStatus()
        }
    }

    companion object{
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }




}
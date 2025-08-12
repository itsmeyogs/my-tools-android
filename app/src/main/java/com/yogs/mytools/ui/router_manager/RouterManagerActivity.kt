package com.yogs.mytools.ui.router_manager

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivityRouterManagerBinding
import com.yogs.mytools.util.setUpAppBar
import com.yogs.mytools.util.showToast

class RouterManagerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRouterManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouterManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAppBar(binding.toolbar)

        checkAndRequestPermissionLocation()
        getStatusConnection()
    }


    private fun getStatusConnection(){
        val tvWifiStatus = binding.tvWifiStatus
        val tvWifiSSID = binding.tvWifiSsid
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        var wifiStatus = getString(R.string.wifi_status_not_connected)
        var wifiSSID = "-"
        if(activeNetwork != null){
            Log.d("activeNetwork" , "object : $activeNetwork")
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            Log.d("networkCapabilities", "object : $networkCapabilities")

            if(networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                wifiStatus = getString(R.string.wifi_status_connected)
                wifiSSID = getWifiSSID(wifiManager)
            }
        }

        tvWifiStatus.text = getString(R.string.wifi_status, wifiStatus)
        tvWifiSSID.text = getString(R.string.wifi_ssid, wifiSSID)
    }

    @Suppress("DEPRECATION")
    private fun getWifiSSID(wifiManager: WifiManager): String{
        val wifiInfo = wifiManager.connectionInfo

        Log.d("network", "object: ${wifiManager.dhcpInfo}")
        Log.d("network2", "object: ${wifiManager.connectionInfo}")
        Log.d("network3", "object: ${wifiManager.wifiState}")
        return wifiInfo.ssid.trim{it == '"'}

//        val dhcpInfo = wifiManager.dhcpInfo

    }

    private fun checkAndRequestPermissionLocation(){
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(permission), LOCATION_PERMISSION_REQUEST_CODE)
        }else{
            getStatusConnection()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getStatusConnection()
            }
        }
    }

    companion object{
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }




}
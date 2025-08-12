package com.yogs.mytools.ui.router_manager

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivityRouterManagerBinding
import com.yogs.mytools.util.setUpAppBar

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
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        var wifiStatus = getString(R.string.wifi_status_not_connected)
        var wifiSSID = "-"
        var ipAddress = "-"
        var defaultGateway = "-"
        if(activeNetwork != null){
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            if(networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                wifiStatus = getString(R.string.wifi_status_connected)
                wifiSSID = getWifiSSID(wifiManager)
                ipAddress = getIpAddress(wifiManager)
                defaultGateway = getDefaultGateway(wifiManager)
            }
        }

        binding.apply {
            tvWifiStatus.text = getString(R.string.wifi_status, wifiStatus)
            tvWifiSsid.text = getString(R.string.wifi_ssid, wifiSSID)
            tvIpAddress.text = getString(R.string.ip_address, ipAddress)
            tvDefaultGateway.text = getString(R.string.default_gateway, defaultGateway)

        }
    }

    @Suppress("DEPRECATION")
    private fun getWifiSSID(wifiManager: WifiManager): String{
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.ssid.trim{ it == '"' }
    }

    @Suppress("DEPRECATION")
    private fun getIpAddress(wifiManager: WifiManager):String{
        val wifiDhcpInfo = wifiManager.dhcpInfo
        return intToIp(wifiDhcpInfo.ipAddress)
    }

    @Suppress("DEPRECATION")
    private fun getDefaultGateway(wifiManager: WifiManager):String{
        val wifiDhcpInfo = wifiManager.dhcpInfo
        return intToIp(wifiDhcpInfo.gateway)
    }

    private fun intToIp(ipAddress: Int): String {
        return (ipAddress and 0xFF).toString() + "." +
                (ipAddress shr 8 and 0xFF) + "." +
                (ipAddress shr 16 and 0xFF) + "." +
                (ipAddress shr 24 and 0xFF)
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
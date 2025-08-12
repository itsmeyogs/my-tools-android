package com.yogs.mytools.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import com.yogs.mytools.data.model.WifiStatus

class RouterManagerRepository(context: Context) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @Suppress("DEPRECATION")
    fun getConnectionStatus(): WifiStatus{
        val activeNetwork = connectivityManager.activeNetwork ?: return WifiStatus(isConnected = false)

        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        if(networkCapabilities == null || !networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
            return WifiStatus(isConnected = false)
        }

        val wifiInfo = wifiManager.connectionInfo
        val dhcpInfo = wifiManager.dhcpInfo

        return WifiStatus(
            isConnected = true,
            ssid = wifiInfo.ssid.trim { it == '"' },
            ipAddress = intToIp(dhcpInfo.ipAddress),
            defaultGateway = intToIp(dhcpInfo.gateway)
        )
    }

    private fun intToIp(ipAddress: Int): String {
        return (ipAddress and 0xFF).toString() + "." +
                (ipAddress shr 8 and 0xFF) + "." +
                (ipAddress shr 16 and 0xFF) + "." +
                (ipAddress shr 24 and 0xFF)
    }


}
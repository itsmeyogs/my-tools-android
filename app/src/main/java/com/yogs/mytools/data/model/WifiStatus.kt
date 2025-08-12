package com.yogs.mytools.data.model

data class WifiStatus(
    val isConnected: Boolean = false,
    val ssid: String = "-",
    val ipAddress: String = "-",
    val defaultGateway: String = "-",
)
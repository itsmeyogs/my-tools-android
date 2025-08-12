package com.yogs.mytools.ui.router_manager

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivityRouterManagerBinding
import com.yogs.mytools.util.setUpAppBar
import com.yogs.mytools.viewmodel.RouterManagerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouterManagerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRouterManagerBinding
    private val viewModel: RouterManagerViewModel by viewModel<RouterManagerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouterManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAppBar(binding.toolbar)

        checkAndRequestPermissionLocation()
        viewModel.getConnectionStatus()
        observeConnectionStatus()
    }


    private fun observeConnectionStatus(){
        viewModel.statusConnection.observe(this){data->
            binding.apply {
                tvWifiStatus.text = getString(R.string.wifi_status, getStringWifiStatus(data.isConnected))
                tvWifiSsid.text = getString(R.string.wifi_ssid, data.ssid)
                tvIpAddress.text = getString(R.string.ip_address, data.ipAddress)
                tvDefaultGateway.text = getString(R.string.default_gateway, data.defaultGateway)
            }
        }
    }

    private fun getStringWifiStatus(value: Boolean):String{
        return if(value) getString(R.string.wifi_status_connected) else getString(R.string.wifi_status_not_connected)
    }





    private fun checkAndRequestPermissionLocation(){
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(permission), LOCATION_PERMISSION_REQUEST_CODE)
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
                viewModel.getConnectionStatus()
            }
        }
    }

    companion object{
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }




}
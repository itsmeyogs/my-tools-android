package com.yogs.mytools.ui.router_manager

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    }


//    private fun getStatusConnection(){
//
//    }

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
                showToast(getString(R.string.permission_location_granted))
            }else{
                showToast(getString(R.string.permission_location_denied))
            }
        }
    }

    companion object{
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


}
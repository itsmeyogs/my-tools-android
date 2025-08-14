package com.yogs.mytools.ui.resolution_changer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivityResolutionChangerBinding
import com.yogs.mytools.util.copyToClipboard
import com.yogs.mytools.util.setUpAppBar
import com.yogs.mytools.util.showToast
import com.yogs.mytools.viewmodel.ResolutionChangerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResolutionChanger : AppCompatActivity() {
    private lateinit var binding: ActivityResolutionChangerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResolutionChangerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAppBar(binding.toolbar)

        if(savedInstanceState == null){
            val selectMethodFragment = SelectMethodResolutionChangerFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.resolution_changer_fragment, selectMethodFragment)
                .commit()
        }


    }


    companion object{
        const val METHOD_ROOT = "root"
        const val METHOD_ADB = "adb"
        const val METHOD_NOT_SELECTED = "not_selected_method"
    }


}
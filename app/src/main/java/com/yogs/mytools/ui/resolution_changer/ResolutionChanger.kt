package com.yogs.mytools.ui.resolution_changer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yogs.mytools.databinding.ActivityResolutionChangerBinding

class ResolutionChanger : AppCompatActivity() {
    private lateinit var binding : ActivityResolutionChangerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResolutionChangerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAppBar()
    }



    private fun setUpAppBar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
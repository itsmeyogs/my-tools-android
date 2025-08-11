package com.yogs.mytools.ui.speedtest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivitySpeedTestBinding
import com.yogs.mytools.util.setUpAppBar

class SpeedTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpeedTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeedTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAppBar(binding.toolbar)

    }
}
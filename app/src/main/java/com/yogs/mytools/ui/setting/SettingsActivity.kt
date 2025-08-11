package com.yogs.mytools.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivitySettingsBinding
import com.yogs.mytools.util.setUpAppBar

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAppBar(binding.toolbar)

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_fragment, SettingsFragment())
                .commit()
        }
    }

}
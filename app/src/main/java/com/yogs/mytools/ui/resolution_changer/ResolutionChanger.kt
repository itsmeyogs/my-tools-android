package com.yogs.mytools.ui.resolution_changer

import android.content.pm.PackageManager
import android.icu.util.Output
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivityResolutionChangerBinding
import com.yogs.mytools.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class ResolutionChanger : AppCompatActivity() {
    private lateinit var binding: ActivityResolutionChangerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResolutionChangerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAppBar()

        val btnCheckPermission = binding.btnCheckPermission

        binding.rgWorkingMode.setOnCheckedChangeListener { _, checkedId ->
            btnCheckPermission.isEnabled = checkedId != -1
        }


        btnCheckPermission.setOnClickListener {
            checkPermission()
        }
    }

    private fun checkPermission() {
        val selectedValue = binding.rgWorkingMode.checkedRadioButtonId
        when (selectedValue) {
            R.id.rb_working_root -> {
                lifecycleScope.launch {
                    val message = withContext(Dispatchers.IO) {
                        try {
                            val process = Runtime.getRuntime().exec("su")
                            process.outputStream.write("id\n".toByteArray())
                            process.outputStream.flush()
                            process.outputStream.write("exit\n".toByteArray())
                            process.outputStream.flush()

                            val output = process.inputStream.bufferedReader().readText().trim()
                            val error = process.errorStream.bufferedReader().readText().trim()
                            val exitCode = process.waitFor()

                            when {
                                output.contains("uid=0") -> "Root granted"
                                exitCode != 0 -> "Root not granted (exit code: $exitCode)"
                                error.isNotEmpty() -> "Root not granted: $error"
                                else -> "Root not granted"
                            }
                        } catch (e: Exception) {
                            "Error: ${e.message}"
                        }
                    }
                    showToast(message)
                }
            }

            R.id.rb_working_adb -> {
                val checkPermission = checkSelfPermission(android.Manifest.permission.WRITE_SECURE_SETTINGS) == PackageManager.PERMISSION_GRANTED
                if(checkPermission){
                    showToast("Granted")
                }else{
                    showToast("not Granted")
                }
            }

            else -> {
                showToast(getString(R.string.please_select_working_mode))
            }
        }
    }


    private fun setUpAppBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
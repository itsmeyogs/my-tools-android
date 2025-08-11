package com.yogs.mytools.data.repository

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class ResolutionChangerRepository(private val context: Context) {

    fun checkPermissionADB():Boolean{
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_SECURE_SETTINGS) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionRoot():Boolean{
        return try {
            val process = Runtime.getRuntime().exec("su")
            process.outputStream.use { output->
                output.write("id\n".toByteArray())
                output.flush()
                output.write("exit\n".toByteArray())
                output.flush()
            }

            val output = process.inputStream.bufferedReader().readText().trim()
            val error = process.errorStream.bufferedReader().readText().trim()
            val exitCode = process.waitFor()
            when {
                output.contains("uid=0") -> true
                exitCode != 0 -> false
                error.isNotEmpty() -> false
                else -> false
            }

        }catch (e: Exception){
            false
        }
    }

}
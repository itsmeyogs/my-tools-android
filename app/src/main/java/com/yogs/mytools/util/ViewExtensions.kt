package com.yogs.mytools.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.yogs.mytools.R


fun AppCompatActivity.setUpAppBar(toolbar: Toolbar){
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
}


fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.copyToClipboard(label: String, text:String, message: String? = null){
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(label, text)
    clipboardManager.setPrimaryClip(clipData)
    val finalMessage = message ?: getString(R.string.message_success_copy_to_clipboard)
    showToast(finalMessage)
}
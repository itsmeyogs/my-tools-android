package com.yogs.mytools

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.yogs.mytools.util.ThemeMode
import java.util.Locale

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_theme),
            getString(R.string.pref_theme_auto)
        )?.apply {
            val theme = ThemeMode.valueOf(this.uppercase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(theme.value)
        }
    }
}
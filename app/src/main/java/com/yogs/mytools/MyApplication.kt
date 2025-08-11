package com.yogs.mytools

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.yogs.mytools.di.appModule
import com.yogs.mytools.util.ThemeMode
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
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

        startKoin{
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
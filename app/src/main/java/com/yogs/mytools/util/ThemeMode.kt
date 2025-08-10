package com.yogs.mytools.util

import androidx.appcompat.app.AppCompatDelegate

enum class ThemeMode(val value:Int) {

    AUTO(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),

    LIGHT(AppCompatDelegate.MODE_NIGHT_NO),

    DARK(AppCompatDelegate.MODE_NIGHT_YES)
}
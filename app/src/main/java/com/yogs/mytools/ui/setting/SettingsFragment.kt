package com.yogs.mytools.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.yogs.mytools.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
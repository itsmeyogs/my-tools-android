package com.yogs.mytools.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.yogs.mytools.R
import com.yogs.mytools.util.ThemeMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_theme))

        themePreference?.setOnPreferenceChangeListener{_, newValue ->
            val theme = ThemeMode.valueOf(newValue.toString().uppercase(Locale.US))
            updateTheme(theme.value)
            true
        }

    }

    private fun updateTheme(theme : Int): Boolean{
        AppCompatDelegate.setDefaultNightMode(theme)
        requireActivity().recreate()
        return true
    }
}
package com.yogs.mytools.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "data")

class DataPreference (private val dataStore: DataStore<Preferences>){


    suspend fun saveSRCWorkingModeKey(workingMode: String){
        Log.d("save Preference", workingMode)
        dataStore.edit { preferences ->
            preferences[SRC_WORKING_MODE_KEY] = workingMode
        }
    }

    fun getSRCWorkingMode() : Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[SRC_WORKING_MODE_KEY]
        }
    }

    suspend fun removeSRCWorkingMode() {
        dataStore.edit { preferences ->
            preferences.remove(SRC_WORKING_MODE_KEY)
        }
    }


    companion object{
        private val SRC_WORKING_MODE_KEY = stringPreferencesKey("src_method_key")
    }

}
package com.yogs.mytools.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yogs.mytools.data.repository.ResolutionChangerRepository
import com.yogs.mytools.ui.resolution_changer.ResolutionChanger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResolutionChangerViewModel(
    private val repository: ResolutionChangerRepository

) : ViewModel(){
    private val _resultPermissionADB = MutableLiveData<Boolean>(false)
    val resultPermissionADB: LiveData<Boolean> get() = _resultPermissionADB

    private val _resultPermissionRoot = MutableLiveData<Boolean>(false)
    val resultPermissionRoot: LiveData<Boolean> get() = _resultPermissionRoot

    fun checkPermission(workingMode: String){
        when (workingMode) {
            ResolutionChanger.METHOD_ROOT -> {
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO){
                        repository.checkPermissionRoot()
                    }
                    Log.d("check root", "$result")
                    _resultPermissionRoot.postValue(result)
                }
            }
            ResolutionChanger.METHOD_ADB -> {
                val result = repository.checkPermissionADB()
                Log.d("check adb", "$result")
                _resultPermissionADB.value = result
            }
            else -> {
                _resultPermissionRoot.value = false
                _resultPermissionADB.value = false
            }
        }
    }



    fun saveWorkingMode(workingMode : String){
        viewModelScope.launch {
            repository.saveWorkingMode(workingMode)
        }
    }

    fun getWorkingMode() : LiveData<String?>{
        return repository.getWorkingMode().asLiveData()
    }

    fun removeWorkingMode(){
        viewModelScope.launch {
            repository.removeWorkingMode()
        }
    }

}
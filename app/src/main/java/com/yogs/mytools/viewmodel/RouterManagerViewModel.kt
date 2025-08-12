package com.yogs.mytools.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogs.mytools.data.model.WifiStatus
import com.yogs.mytools.data.repository.RouterManagerRepository
import kotlinx.coroutines.launch

class RouterManagerViewModel(private val repository: RouterManagerRepository) : ViewModel() {

    private val _statusConnection = MutableLiveData<WifiStatus>(WifiStatus())
    val statusConnection : LiveData<WifiStatus> get() = _statusConnection

    fun getConnectionStatus(){
        viewModelScope.launch {
           val result =  repository.getConnectionStatus()
            _statusConnection.value = result
        }
    }

}
package com.yogs.mytools.ui.home

import androidx.lifecycle.ViewModel
import com.yogs.mytools.data.DataRepository
import com.yogs.mytools.data.model.Tool

class HomeViewModel(private val repository: DataRepository): ViewModel() {

    fun getTools() : List<Tool>{
      return repository.getTools()
    }

}
package com.yogs.mytools.data

import android.content.Context
import com.yogs.mytools.data.model.Tool

class DataRepository(private val context:Context) {

    fun getTools():List<Tool>{
        return DataProvider.getTools(context)
    }

}
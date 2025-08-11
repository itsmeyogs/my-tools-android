package com.yogs.mytools.data

import android.content.Context
import androidx.core.content.ContextCompat
import com.yogs.mytools.R
import com.yogs.mytools.data.model.Tool

object DataProvider{

    fun getTools(context: Context) : List<Tool>{
        return listOf(
            Tool(
                key = ContextCompat.getString(context, R.string.key_screen_resolution_changer),
                image =R.drawable.ic_screen_resolution_changer,
                title = ContextCompat.getString(context, R.string.title_screen_resolution_changer),
                desc = ContextCompat.getString(context, R.string.desc_screen_resolution_changer)
            ),
            Tool(
                key = ContextCompat.getString(context, R.string.key_fh_pw_generator),
                image = R.drawable.ic_fh_pw_generator,
                title = ContextCompat.getString(context, R.string.title_fh_pw_generator),
                desc = ContextCompat.getString(context, R.string.desc_fh_pw_generator)
            )
        )

    }

}
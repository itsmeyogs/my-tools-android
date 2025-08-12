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
                key = ContextCompat.getString(context, R.string.key_speed_test),
                image = R.drawable.ic_speed_test,
                title = ContextCompat.getString(context, R.string.title_speed_test),
                desc = ContextCompat.getString(context, R.string.desc_speed_test)
            ),
            Tool(
                key = ContextCompat.getString(context, R.string.key_router_manager),
                image = R.drawable.ic_router_manager,
                title = ContextCompat.getString(context, R.string.title_router_manager),
                desc = ContextCompat.getString(context, R.string.desc_router_manager)
            ),
            Tool(
                key = ContextCompat.getString(context, R.string.key_fh_pw_generator),
                image = R.drawable.ic_fh_pw_generator,
                title = ContextCompat.getString(context, R.string.title_fh_pw_generator),
                desc = ContextCompat.getString(context, R.string.desc_fh_pw_generator)
            ),

        )
    }

    fun getSpeedTestHtml() : String{
        val htmlData = """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body, html { margin: 0; padding: 0; height: 100%; overflow: hidden; }
                    iframe { width: 100%; height: 100%; border: none; }
                </style>
            </head>
            <body>
                <iframe src="https://openspeedtest.com/Get-widget.php"></iframe>
            </body>
            </html>
        """.trimIndent()
        return htmlData
    }

}
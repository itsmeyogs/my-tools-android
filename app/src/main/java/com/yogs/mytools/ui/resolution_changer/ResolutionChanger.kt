package com.yogs.mytools.ui.resolution_changer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivityResolutionChangerBinding
import com.yogs.mytools.util.setUpAppBar
import com.yogs.mytools.viewmodel.ResolutionChangerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResolutionChanger : AppCompatActivity() {
    private lateinit var binding: ActivityResolutionChangerBinding
    private val viewModel : ResolutionChangerViewModel by viewModel<ResolutionChangerViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResolutionChangerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAppBar(binding.toolbar)


        viewModel.getWorkingMode().observe(this){ methodWorking ->
            Log.d("preference methodWorking", "$methodWorking")
            if(savedInstanceState == null){
                if(methodWorking == null){
                    showSelectMethodFragment()
                }else{
                    viewModel.checkPermission(methodWorking)
                    when(methodWorking){
                        METHOD_ROOT -> {
                            val result  = viewModel.resultPermissionRoot.value
                            if(result == true) showMainFragment() else showSelectMethodFragment()
                        }
                        METHOD_ADB -> {
                            val result = viewModel.resultPermissionADB.value
                            if(result == true) showMainFragment() else showSelectMethodFragment()
                        }
                        else -> {
                            viewModel.removeWorkingMode()
                            showSelectMethodFragment()
                        }
                    }
                }
            }
        }
    }


    private fun showMainFragment(){
        val fragment = MainResolutionChangerFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.resolution_changer_fragment, fragment)
            .commit()
    }

    private fun showSelectMethodFragment(){
        val fragment = SelectMethodResolutionChangerFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.resolution_changer_fragment, fragment)
            .commit()
    }


    companion object{
        const val METHOD_ROOT = "root"
        const val METHOD_ADB = "adb"
        const val METHOD_NOT_SELECTED = "not_selected_method"
    }


}
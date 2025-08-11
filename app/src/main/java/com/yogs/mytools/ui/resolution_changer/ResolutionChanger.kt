package com.yogs.mytools.ui.resolution_changer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Visibility
import com.yogs.mytools.R
import com.yogs.mytools.databinding.ActivityResolutionChangerBinding
import com.yogs.mytools.util.showToast
import com.yogs.mytools.viewmodel.ResolutionChangerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResolutionChanger : AppCompatActivity() {
    private lateinit var binding: ActivityResolutionChangerBinding
    private val viewModel : ResolutionChangerViewModel by viewModel<ResolutionChangerViewModel>()

    companion object{
        const val METHOD_ROOT = "root"
        const val METHOD_ADB = "adb"
        const val METHOD_NOT_SELECTED = "not_selected_method"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResolutionChangerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAppBar()

        observePermissionStatus()
        observeSelectedMethod()

        binding.btnCheckPermission.setOnClickListener {
            checkPermissionStatus()
        }



    }

    private fun observeSelectedMethod(){
        binding.rgWorkingMode.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId != -1){
                checkPermissionStatus()
                binding.cardPermissionStatus.visibility = View.VISIBLE
                binding.btnCheckPermission.visibility = View.VISIBLE
            }
        }
    }

    private fun checkPermissionStatus(){
        val selectedOption = binding.rgWorkingMode.checkedRadioButtonId
        when(selectedOption){
            R.id.rb_working_root -> {
                viewModel.checkPermission(METHOD_ROOT)
                Log.d("check", "Run working root")
            }
            R.id.rb_working_adb -> {
                viewModel.checkPermission(METHOD_ADB)
                Log.d("check", "Run working adb")
            }
            else -> {
                viewModel.checkPermission(METHOD_NOT_SELECTED)
            }
        }
    }


    private fun observePermissionStatus(){
        val tvPermissionStatus = binding.tvPermissionStatus

        viewModel.resultPermissionRoot.observe(this){
            tvPermissionStatus.text = getStringPermissionStatus(it)
        }

        viewModel.resultPermissionADB.observe(this){
            tvPermissionStatus.text = getStringPermissionStatus(it)
        }
    }

    private fun getStringPermissionStatus(value: Boolean) : String{
        val result = if(value) getString(R.string.permission_granted) else getString(R.string.permission_not_granted)
        Log.d("check result", result)
        return getString(R.string.permission_status, result)
    }


    private fun setUpAppBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
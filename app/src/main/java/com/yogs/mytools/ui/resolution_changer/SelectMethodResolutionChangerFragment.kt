package com.yogs.mytools.ui.resolution_changer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.yogs.mytools.R
import com.yogs.mytools.databinding.FragmentSelectMethodResolutionChangerBinding
import com.yogs.mytools.util.copyToClipboard
import com.yogs.mytools.util.showToast
import com.yogs.mytools.viewmodel.ResolutionChangerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectMethodResolutionChangerFragment : Fragment() {

    private var _binding : FragmentSelectMethodResolutionChangerBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ResolutionChangerViewModel by viewModel<ResolutionChangerViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectMethodResolutionChangerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observePermissionStatus()
        observePermissionStatus()
        observeSelectedMethod()

        binding.btnCheckOrNext.setOnClickListener {
            manageActionBtnCheckOrNext()
        }

        binding.btnCopyCode.setOnClickListener {
            requireContext().copyToClipboard(getString(R.string.copy_code), getString(R.string.code_tutorial_give_permission_adb))
        }


    }

    private fun manageActionBtnCheckOrNext(){
        val statusPermissionRoot = viewModel.resultPermissionRoot.value
        val statusPermissionADB = viewModel.resultPermissionADB.value
        when(val methodId = binding.rgWorkingMode.checkedRadioButtonId){
            R.id.rb_working_root ->{
                if(statusPermissionRoot == true){
                    actionNextButton()
                }else{
                    actionCheckPermissionButton(methodId)
                }
            }

            R.id.rb_working_adb -> {
                if(statusPermissionADB == true){
                    actionNextButton()
                }else{
                    actionCheckPermissionButton(methodId)
                }
            }
            else -> {
                requireContext().showToast(getString(R.string.unknown_action))
            }
        }

    }

    private fun actionNextButton(){
        requireContext().showToast("Next Clicked")
    }

    private fun actionCheckPermissionButton(selectedMethodId : Int){
        showLoadingCheckPermission()
        checkPermissionStatus(selectedMethodId)
    }

    private fun checkPermissionStatus(idMethodWorking: Int){
        when(idMethodWorking){
            R.id.rb_working_root -> {
                viewModel.checkPermission(ResolutionChanger.METHOD_ROOT)
            }
            R.id.rb_working_adb -> {
                viewModel.checkPermission(ResolutionChanger.METHOD_ADB)
            }
            else -> {
                viewModel.checkPermission(ResolutionChanger.METHOD_NOT_SELECTED)
            }
        }
    }


    private fun showLoadingCheckPermission(){
        binding.apply {
            lifecycleScope.launch {
                tvPermissionStatus.visibility = View.INVISIBLE
                loadingPermissionStatus.visibility = View.VISIBLE
                delay(2000)
                tvPermissionStatus.visibility = View.VISIBLE
                loadingPermissionStatus.visibility = View.GONE
            }
        }
    }

    private fun observeSelectedMethod(){
        binding.rgWorkingMode.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId != -1){
                checkPermissionStatus(checkedId)
                binding.cardPermissionStatus.visibility = View.VISIBLE
                binding.btnCheckOrNext.visibility = View.VISIBLE
            }
            manageVisibilityAndTextBtnCheckOrNext(checkedId)
        }
    }

    private fun observePermissionStatus(){

        viewModel.resultPermissionRoot.observe(viewLifecycleOwner){ status ->
            binding.tvPermissionStatus.text = getStringPermissionStatus(status)
            manageTextBtnCheckOrNext()
        }

        viewModel.resultPermissionADB.observe(viewLifecycleOwner){ status ->
            binding.tvPermissionStatus.text = getStringPermissionStatus(status)
            manageTextBtnCheckOrNext()
        }
    }

    private fun manageTextBtnCheckOrNext(){
        val selectedMethodId = binding.rgWorkingMode.checkedRadioButtonId
        manageVisibilityAndTextBtnCheckOrNext(selectedMethodId)
    }

    private fun getStringPermissionStatus(value: Boolean) : String{
        val result = if(value) getString(R.string.permission_granted) else getString(R.string.permission_not_granted)
        Log.d("check result", result)
        return getString(R.string.permission_status, result)
    }


    private fun manageVisibilityAndTextBtnCheckOrNext(idMethodWorking: Int){
        val statusPermissionRoot = viewModel.resultPermissionRoot.value
        val statusPermissionADB = viewModel.resultPermissionADB.value

        val cardTutorialGivePermissionADB = binding.cardTutorialGivePermissionAdb
        val btnCheckOrNext = binding.btnCheckOrNext

        when(idMethodWorking){
            R.id.rb_working_root -> {
                cardTutorialGivePermissionADB.visibility = View.GONE
                if(statusPermissionRoot == true){
                    btnCheckOrNext.text = getString(R.string.next)
                }else{
                    btnCheckOrNext.text = getString(R.string.check_permission)
                }
            }
            R.id.rb_working_adb -> {
                cardTutorialGivePermissionADB.visibility = View.VISIBLE
                if(statusPermissionADB == true){
                    btnCheckOrNext.text = getString(R.string.next)
                }else{
                    btnCheckOrNext.text = getString(R.string.check_permission)
                }
            }
            else -> {
                btnCheckOrNext.visibility = View.INVISIBLE
                cardTutorialGivePermissionADB.visibility = View.GONE
            }
        }

    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
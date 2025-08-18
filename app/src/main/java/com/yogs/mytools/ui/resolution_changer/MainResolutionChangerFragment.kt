package com.yogs.mytools.ui.resolution_changer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yogs.mytools.R
import com.yogs.mytools.databinding.FragmentMainResolutionChangerBinding
import com.yogs.mytools.viewmodel.ResolutionChangerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainResolutionChangerFragment : Fragment() {
    private var _binding : FragmentMainResolutionChangerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResolutionChangerViewModel by viewModel<ResolutionChangerViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _binding = FragmentMainResolutionChangerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDisplayInfo()


    }

    private fun showDisplayInfo(){
        val displayInfo = viewModel.getDisplayInfo()
        val resolution = displayInfo.screenWidth.toString()+"x"+displayInfo.screenHeight.toString()
        binding.apply {
            tvCurrentResolution.text = getString(R.string.current_resolution, resolution)
            tvCurrentDpi.text = getString(R.string.current_dpi, displayInfo.dpi.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
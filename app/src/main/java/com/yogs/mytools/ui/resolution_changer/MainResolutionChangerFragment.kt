package com.yogs.mytools.ui.resolution_changer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yogs.mytools.databinding.FragmentMainResolutionChangerBinding

class MainResolutionChangerFragment : Fragment() {
    private var _binding : FragmentMainResolutionChangerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _binding = FragmentMainResolutionChangerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTest.text = ResolutionChanger.METHOD_ADB

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
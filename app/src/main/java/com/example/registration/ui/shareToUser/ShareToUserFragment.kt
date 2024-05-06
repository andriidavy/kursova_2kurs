package com.example.registration.ui.shareToUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.databinding.FragmentShareToUserBinding
import com.example.registration.global.FileMappingObj
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShareToUserFragment : Fragment() {
    private lateinit var binding: FragmentShareToUserBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<ShareToUserViewModel>()
    private lateinit var sharedFilePublicUrl: String
    private lateinit var sharedFileName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShareToUserBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
    }

    private fun setViews() {
        navController = findNavController()
        sharedFilePublicUrl = requireArguments().getString("itemPublicUrl") ?: ""
        sharedFileName = requireArguments().getString("itemName") ?: ""
    }

    private fun setListeners() = with(binding) {
        btUpload.setOnClickListener {
            val guestUserName = etUsername.text.toString()
            lifecycleScope.launch {
                viewModel.shareFile(
                    guestUserName,
                    FileMappingObj.createMultipartFromString(sharedFilePublicUrl, sharedFileName),
                    sharedFileName
                ).collect { result ->
                    result.onSuccess { finishUrl ->
                        ToastObj.longToastMake("Файл завантажено: $finishUrl", context)
                    }
                    result.onFailure { ToastObj.longToastMake("Файл не завантажено", context) }
                }
            }
        }
    }
}
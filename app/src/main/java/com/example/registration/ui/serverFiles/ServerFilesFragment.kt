package com.example.registration.ui.serverFiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.databinding.FragmentServerFilesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServerFilesFragment : Fragment() {
    private lateinit var binding: FragmentServerFilesBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<ServerFilesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServerFilesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        btFileSearch.setOnClickListener {
            val pathToFolder = etWayToFolder.text.toString()
            viewModel.getFilesFromServerFolder(pathToFolder)
        }
    }
}
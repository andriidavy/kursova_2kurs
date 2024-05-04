package com.example.registration.ui.serverFiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ServerFilesListAdapter
import com.example.registration.databinding.FragmentServerFilesBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.directoryItem.ServerItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ServerFilesFragment : Fragment() {
    private lateinit var binding: FragmentServerFilesBinding
    private lateinit var adapter: ServerFilesListAdapter
    private lateinit var navController: NavController
    private lateinit var itemsList: List<ServerItem>
    private lateinit var pathToFolder: String
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

    private fun setupViews() = with(binding) {
        adapter = ServerFilesListAdapter(emptyList(), itemRemovedClick())
        rvFilesList.adapter = adapter
        rvFilesList.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        btFileSearch.setOnClickListener {
            pathToFolder = etWayToFolder.text.toString()
            itemListUpdate(pathToFolder)
        }
    }

    private fun itemListUpdate(path: String) {
        lifecycleScope.launch {
            viewModel.getFilesFromServerFolder(path).collect { result ->
                result.onSuccess { listOfItem ->
                    adapter.updateCart(listOfItem)
                    itemsList = listOfItem
                }
                result.onFailure {
                    ToastObj.longToastMake("Файли не знайдено!", context)
                }
            }
        }
    }

    private fun itemRemovedClick(): (Int) -> Unit {
        return { position ->
            itemsList.getOrNull(position)?.url?.let { url ->
                viewModel.deleteFile(url)
                itemListUpdate(pathToFolder)
                ToastObj.shortToastMake("Файл видалено!", context)
            }
        }
    }
}
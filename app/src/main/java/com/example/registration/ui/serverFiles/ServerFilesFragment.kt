package com.example.registration.ui.serverFiles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.ServerFilesListAdapter
import com.example.registration.databinding.FragmentServerFilesBinding
import com.example.registration.global.KeyboardObj
import com.example.registration.global.ToastObj
import com.example.registration.model.directoryItem.ServerItem
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.internal.ViewUtils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.URLEncoder

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
        adapter = ServerFilesListAdapter(
            emptyList(),
            itemRemovedClick(),
            itemClick(),
            itemDownloadClick()
        )
        rvFilesList.adapter = adapter
        rvFilesList.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        btFileSearch.setOnClickListener {
            refreshPath()
            itemListUpdate(pathToFolder)
        }

        btToCreateFolder.setOnClickListener {
            viewSettings(2)
            etNewFolderName.requestFocus()
            KeyboardObj.showKeyboard(requireActivity())
        }

        root.setOnClickListener {
            viewSettings(1)
            KeyboardObj.hideKeyboard(requireActivity(), requireView())
        }

        btCreateFolderConfirm.setOnClickListener {
            viewSettings(1)
            createNewFolder()
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

    private fun createNewFolder() = with(binding) {
        val newFolderName = etNewFolderName.text.toString()
        val encodedFilePath = URLEncoder.encode(pathToFolder, "UTF-8")
        viewModel.createFolder(encodedFilePath, newFolderName)
        itemListUpdate(pathToFolder)
        ToastObj.shortToastMake("Створено нову папку!", context)
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

    private fun itemClick(): (Int) -> Unit = with(binding) {
        return { position ->
            itemsList.getOrNull(position)?.name?.let { name ->
                val newPath = if (pathToFolder.isNotEmpty()) {
                    "$pathToFolder/$name"
                } else {
                    name
                }
                etWayToFolder.setText(newPath)
                refreshPath()
                itemListUpdate(pathToFolder)
            }
        }
    }

    private fun refreshPath() = with(binding) {
        pathToFolder = etWayToFolder.text.toString()
    }

    private fun itemDownloadClick(): (Int) -> Unit {
        return { position ->
            val itemResp = itemsList.getOrNull(position)

            itemResp?.let { item ->
                lifecycleScope.launch {
                    viewModel.downloadFile(item.url).collect { result ->
                        result.onSuccess { responseBody ->
                            val downloadRes = viewModel.saveFileToDevice(responseBody, item.name)
                            if (downloadRes) {
                                ToastObj.longToastMake("файл завантажено", context)
                            } else {
                                ToastObj.longToastMake("файл не завантажено", context)
                            }
                        }
                        result.onFailure {
                            ToastObj.longToastMake("помилка завантаження", context)
                        }
                    }
                }
            }
        }
    }

    private fun viewSettings(settingsType: Int) = with(binding) {
        if (settingsType == 1) {
            btToCreateFolder.visibility = View.VISIBLE
            btCreateFolderConfirm.visibility = View.INVISIBLE
            etNewFolderName.visibility = View.INVISIBLE
        } else if (settingsType == 2) {
            btToCreateFolder.visibility = View.INVISIBLE
            btCreateFolderConfirm.visibility = View.VISIBLE
            etNewFolderName.visibility = View.VISIBLE
            etNewFolderName.setText("")
        }
    }
}
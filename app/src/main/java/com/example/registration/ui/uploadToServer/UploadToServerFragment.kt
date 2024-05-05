package com.example.registration.ui.uploadToServer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ServerFilesListAdapter
import com.example.registration.databinding.FragmentUploadToServerBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class UploadToServerFragment : Fragment() {
    private lateinit var binding: FragmentUploadToServerBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<UploadToServerViewModel>()
    private lateinit var fileFromDevice: File
    private lateinit var fileName: String

    private val PICK_FILE_REQUEST_CODE = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadToServerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
    }

    private fun setupViews() {
        navController = findNavController()
    }

    private fun setObservers() = with(binding) {
        viewModel.isFileSelectedLiveData.observe(viewLifecycleOwner) { isSelected ->
            btUpload.apply {
                isEnabled = isSelected
                alpha = if (isSelected) 1f else 0.5f
            }
        }
    }

    private fun setListeners() = with(binding) {
        btFileChoose.setOnClickListener {
            selectFile()
        }

        btUpload.setOnClickListener {
            val path = etWayToFolder.text.toString()
            lifecycleScope.launch {
                viewModel.uploadFile(fileFromDevice, path, fileName).collect { result ->
                    result.onSuccess { finishUrl ->
                        ToastObj.longToastMake("Файл завантажено: $finishUrl", context)
                    }
                    result.onFailure { ToastObj.longToastMake("Файл не завантажено", context) }
                }
            }
        }
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Указываем тип файлов, которые можно выбирать (все файлы)
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // Обработка выбранного файла
                fileFromDevice = getFileFromUri(uri)
                fileName = getFileName(uri)
                // Теперь у вас есть выбранный файл (selectedFile), который можно использовать в вашем приложении
                viewModel.setFileSelected(true)
            }
        }
    }

    private fun getFileFromUri(uri: Uri): File {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val outputFile = File(requireActivity().cacheDir, "selected_file")
        inputStream?.copyTo(outputFile.outputStream())
        inputStream?.close()
        return outputFile
    }

    private fun getFileName(uri: Uri): String {
        var filename = ""
        try {
            val cursor = requireActivity().contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    filename = it.getString(displayNameIndex)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return filename
    }
}
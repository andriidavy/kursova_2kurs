package com.example.registration.ui.uploadToServer

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.databinding.FragmentUploadToServerBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

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
                        ToastObj.longToastMake("Файл завантажено", context)
                    }
                    result.onFailure { ToastObj.longToastMake("Файл не завантажено", context) }
                }
            }
        }
    }

    private fun selectFile() {
        val options = arrayOf<CharSequence>("Обрати з файлосховища", "Зробити фото")
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Оберіть джерело файлу")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Обрати з файлосховища" -> {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "*/*" // Фильтр только для изображений: "image/*"
                    startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
                }
                options[item] == "Зробити фото" -> {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                        startActivityForResult(takePictureIntent, PICK_FILE_REQUEST_CODE)
                    } else {
                        ToastObj.longToastMake("Відсутній додаток для камери", context)
                    }
                }
            }
        }
        builder.show()
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
            if (data?.extras?.containsKey("data") == true) { // Если есть данные (фото), полученные из камеры
                val imageBitmap = data.extras?.get("data") as Bitmap
                fileFromDevice = bitmapToFile(imageBitmap)
                fileName = "photo_${System.currentTimeMillis()}.jpg"
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

    private fun bitmapToFile(bitmap: Bitmap): File {
        val filesDir = requireContext().filesDir
        val imageFile = File(filesDir, "image.jpg")
        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return imageFile
    }
}
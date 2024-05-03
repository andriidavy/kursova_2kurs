package com.example.registration.ui.fileManage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentFileManageBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileManageFragment : Fragment() {
    private lateinit var binding: FragmentFileManageBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<FileManageViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileManageBinding.inflate(inflater)
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
        btToCreateFolder.setOnClickListener {
            btToCreateFolder.visibility = View.INVISIBLE
            btCreateFolderConfirm.visibility = View.VISIBLE
            etFolderName.visibility = View.VISIBLE
            etFolderName.requestFocus()
            showKeyboard()
        }

        root.setOnClickListener {
            btToCreateFolder.visibility = View.VISIBLE
            btCreateFolderConfirm.visibility = View.INVISIBLE
            etFolderName.visibility = View.INVISIBLE
            hideKeyboard()
        }

        btCreateFolderConfirm.setOnClickListener {
            btToCreateFolder.visibility = View.VISIBLE
            btCreateFolderConfirm.visibility = View.INVISIBLE
            etFolderName.visibility = View.INVISIBLE
            ToastObj.longToastMake("Папку створено", context)
            val folderName = etFolderName.text.toString()
            viewModel.createFolder(folderName)
        }

        btToServerFiles.setOnClickListener {
            navController.navigate(R.id.action_fileManageFragment_to_serverFilesFragment)
        }
    }

    private fun showKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}
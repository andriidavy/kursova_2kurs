package com.example.registration.ui.user.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.registration.R
import com.example.registration.databinding.FragmentUserProfileBinding
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.FileMappingObj
import com.example.registration.model.users.data.UserDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfilePageFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var navController: NavController
    private val viewModelDataStore by viewModels<DataStoreViewModel>()
    private val viewModel by viewModels<UserProfilePageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = viewModelDataStore.getUser()
        setupView()
        setListeners(user)
        setObservers(user)
    }

    private fun setupView() {
        navController = findNavController()

        arguments?.getString("imageUrl")?.let { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.baseline_upload_file_24) // Placeholder для случая, если изображение еще не загружено
                .error(R.drawable.baseline_error_outline_24) // Изображение для случая, если произошла ошибка загрузки
                .into(binding.imageView)
        }
    }

    private fun setListeners(user: UserDTO?) = with(binding) {
        buttonLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout(user).collect { logoutResult ->
                    logoutResult.onSuccess {}
                    logoutResult.onFailure {}
                }
            }
            navController.navigate(R.id.action_userProfilePageFragment_to_loginFragment)
        }

        buttonChangeDate.setOnClickListener {
            lifecycleScope.launch {
                navController.navigate(R.id.action_userProfilePageFragment_to_profileEditFragment)
            }
        }

        imageView.setOnClickListener {
            navController.navigate(R.id.action_userProfilePageFragment_to_pickPhotoFragment)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
//            val selectedImageUri: Uri? = data.data
//            selectedImageUri?.let { uri ->
//                val imageName = FileMappingObj.getFileName(uri, requireActivity())
//                val imageFile = FileMappingObj.getFileFromUri(uri, requireActivity())
//
//                Glide.with(this)
//                    .load(uri)
//                    .placeholder(R.drawable.baseline_upload_file_24) // Placeholder для случая, если изображение еще не загружено
//                    .error(R.drawable.baseline_error_outline_24) // Изображение для случая, если произошла ошибка загрузки
//                    .into(binding.imageView)
//            }
//        }
//    }

    private fun setObservers(user: UserDTO?) = with(binding) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                user?.let { user ->
                    customerId.text = user.objectId
                    customerName.text = user.name
                    customerEmail.text = user.email
                    customerNationality.text = user.nationality
                    customerAge.text = user.age.toString()
                    customerGender.text = user.gender
                }
            }
        }
    }



    companion object {
        const val GALLERY_REQUEST_CODE = 100
    }
}
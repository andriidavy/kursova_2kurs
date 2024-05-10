package com.example.registration.ui.user.profile.photo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ChooseFileAdapter
import com.example.registration.adapter.ServerFilesListAdapter
import com.example.registration.databinding.FragmentPickPhotoBinding
import com.example.registration.databinding.FragmentUserProfileBinding
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import com.example.registration.model.directoryItem.ServerItem
import com.example.registration.model.users.data.ImageDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PickPhotoFragment : Fragment() {
    private lateinit var binding: FragmentPickPhotoBinding
    private lateinit var adapter: ChooseFileAdapter
    private lateinit var navController: NavController
    private lateinit var itemsList: List<ServerItem>
    private val viewModel by viewModels<PickPhotoViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickPhotoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        itemListUpdate()
    }

    private fun setupView() = with(binding) {
        adapter = ChooseFileAdapter(
            emptyList(),
            itemClick()
        )
        rvFilesList.adapter = adapter
        rvFilesList.layoutManager = LinearLayoutManager(activity)
        navController = findNavController()
    }

    private fun itemListUpdate() {
        lifecycleScope.launch {
            viewModel.getAllFiles().collect { result ->
                result.onSuccess { listOfItem ->
                    val filteredList =
                        listOfItem.filter { it.name.endsWith(".jpg", ignoreCase = true) }
                    adapter.updateCart(filteredList)
                    itemsList = filteredList
                }
                result.onFailure {
                    ToastObj.longToastMake("Файли не знайдено!", context)
                }
            }
        }
    }

    private fun itemClick(): (Int) -> Unit = with(binding) {
        return { position ->
            val bundle = Bundle()
            val token = dataStoreViewModel.getUser()?.userToken ?: ""
            val id = dataStoreViewModel.getUser()?.objectId ?: ""
            itemsList.getOrNull(position)?.publicUrl?.let { url ->
                val imageDTO = ImageDTO(token, id, url)
                lifecycleScope.launch {
                    viewModel.updateUserProfileImage(imageDTO).collect { result ->
                        result.onSuccess { imageDTO ->
                            val resultImageUrl = imageDTO.profilePhotoUrl
                            bundle.putString("imageUrl", resultImageUrl)

                            navController.navigate(
                                R.id.action_pickPhotoFragment_to_userProfilePageFragment,
                                bundle
                            )
                        }
                        result.onFailure {
                            ToastObj.longToastMake("Зображення не завантажено!", context)
                        }
                    }
                }
            }
        }
    }
}
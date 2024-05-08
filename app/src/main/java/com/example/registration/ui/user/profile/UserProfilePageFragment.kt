package com.example.registration.ui.user.profile

import android.os.Bundle
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
import com.example.registration.R
import com.example.registration.databinding.FragmentUserProfileBinding
import com.example.registration.datastore.DataStoreViewModel
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
    }

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
}
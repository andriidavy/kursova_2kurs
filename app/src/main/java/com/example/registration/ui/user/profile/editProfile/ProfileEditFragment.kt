package com.example.registration.ui.user.profile.editProfile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentProfileEditBinding
import com.example.registration.databinding.FragmentUserProfileBinding
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import com.example.registration.model.users.User
import com.example.registration.model.users.data.UserDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileEditFragment : Fragment() {
    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<ProfileEditViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setListeners()
    }

    private fun setupView() = with(binding) {
        navController = findNavController()
        customerId.text = dataStoreViewModel.getUser()?.objectId ?: ""
        customerName.setText(dataStoreViewModel.getUser()?.name ?: "")
        customerEmail.setText(dataStoreViewModel.getUser()?.email ?: "")
        customerNationality.setText(dataStoreViewModel.getUser()?.nationality ?: "")
        customerAge.setText(dataStoreViewModel.getUser()?.age.toString())
        rgGender.check(0)

    }

    private fun setListeners() = with(binding) {
        buttonUpdate.setOnClickListener {
            val token = dataStoreViewModel.getUser()?.userToken ?: ""
            val id = dataStoreViewModel.getUser()?.objectId ?: ""
            val oldName = dataStoreViewModel.getUser()?.name ?: ""
            val name = customerName.text.toString()
            val email = customerEmail.text.toString()
            val nationality = customerNationality.text.toString()
            val age = customerAge.text.toString().toInt()
            val gender = if (radioButtonMale.isChecked) {
                User.Gender.MALE.text
            } else {
                User.Gender.FEMALE.text
            }
            val updatedUser = UserDTO(token, id, name, email, nationality, age, gender)
            lifecycleScope.launch {
                viewModel.updateUser(token, updatedUser).collect { result ->
                    result.onSuccess { user ->
                        viewModel.renameFolder(token, oldName, name).collect { renameResult ->
                            renameResult.onSuccess { res -> Log.e("renameResult", res) }
                            renameResult.onFailure {
                                ToastObj.longToastMake(
                                    "Помилка перейменування директорії!",
                                    context
                                )
                            }
                        }
                        ToastObj.longToastMake("Дані оновлено!", context)
                    }
                    result.onFailure {

                        ToastObj.longToastMake("Помилка оновлення даних!", context)
                    }
                }
            }
            navController.navigate(R.id.action_profileEditFragment_to_loginFragment)
        }
    }
}
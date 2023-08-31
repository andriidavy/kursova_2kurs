package com.example.registration.ui.manager.adminMode.managers

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.model.users.Manager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddManagerViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    fun addManager(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Flow<Result<Manager>> {
        return managerRepository.saveManager(name, surname, email, password)
    }
}
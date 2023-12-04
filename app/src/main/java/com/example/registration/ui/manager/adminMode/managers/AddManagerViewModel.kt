package com.example.registration.ui.manager.adminMode.managers

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
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
        password: String,
        repPassword: String
    ): Flow<Result<Int>> {
        return managerRepository.insertManager(name, surname, email, password, repPassword)
    }
}
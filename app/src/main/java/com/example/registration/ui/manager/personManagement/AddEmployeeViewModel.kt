package com.example.registration.ui.manager.personManagement

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    fun addEmployee(
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> {
        return managerRepository.insertEmployee(name, surname, email, password, repPassword)
    }
}
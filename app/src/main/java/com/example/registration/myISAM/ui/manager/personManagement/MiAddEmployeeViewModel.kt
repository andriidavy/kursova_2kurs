package com.example.registration.myISAM.ui.manager.personManagement

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.myIsam.manager.MiManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MiAddEmployeeViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    fun addEmployee(
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> {
        return miManagerRepository.insertEmployee(name, surname, email, password, repPassword)
    }
}
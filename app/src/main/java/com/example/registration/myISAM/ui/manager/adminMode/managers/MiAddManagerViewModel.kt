package com.example.registration.myISAM.ui.manager.adminMode.managers

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.myIsam.manager.MiManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MiAddManagerViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    fun addManager(
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> {
        return miManagerRepository.insertManager(name, surname, email, password, repPassword)
    }
}
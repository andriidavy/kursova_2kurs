package com.example.registration.myISAM.ui.manager.adminMode.managers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.myIsam.manager.MiManagerRepository
import com.example.registration.model.users.ManagerProfileDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiEditManagerViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    private val _managerAllArray = MutableStateFlow<List<ManagerProfileDTO>>(emptyList())
    val managerAllArray: StateFlow<List<ManagerProfileDTO>>
        get() = _managerAllArray

    init {
        getAllManagersProfileDTO()
    }

    fun getAllManagersProfileDTO() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miManagerRepository.getAllManagersProfileDTO()
            withContext(Dispatchers.Main) {
                result.collect {
                    _managerAllArray.value = it
                }
            }
        }
    }

    fun deleteManagerById(managerId: Int): Flow<Result<Unit>> {
        return miManagerRepository.deleteManagerById(managerId)
    }
}
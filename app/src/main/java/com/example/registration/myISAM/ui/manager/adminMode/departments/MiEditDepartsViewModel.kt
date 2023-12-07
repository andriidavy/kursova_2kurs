package com.example.registration.myISAM.ui.manager.adminMode.departments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.manager.MiManagerRepository
import com.example.registration.model.department.DepartmentDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiEditDepartsViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    private val _departAllArray = MutableStateFlow<List<DepartmentDTO>>(emptyList())
    val departAllArray: StateFlow<List<DepartmentDTO>>
        get() = _departAllArray

    init {
        getAllDepartments()
    }

    private fun getAllDepartments() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miManagerRepository.getAllDepartments()
            withContext(Dispatchers.Main) {
                result.collect {
                    _departAllArray.value = it
                }
            }
        }
    }
}
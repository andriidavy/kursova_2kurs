package com.example.registration.myISAM.ui.manager.adminMode.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.manager.MiManagerRepository
import com.example.registration.model.users.StaffDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiStaffViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    private val _staffArray = MutableStateFlow<List<StaffDTO>>(emptyList())
    val staffArray: StateFlow<List<StaffDTO>>
        get() = _staffArray

    init {
        getStaff()
    }

    private fun getStaff() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miManagerRepository.getStaff()
            withContext(Dispatchers.Main) {
                result.collect {
                    _staffArray.value = it
                }
            }
        }
    }
}
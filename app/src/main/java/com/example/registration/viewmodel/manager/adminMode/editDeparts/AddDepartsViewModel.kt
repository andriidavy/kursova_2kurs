package com.example.registration.viewmodel.manager.adminMode.editDeparts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDepartsViewModel(private val managerRepository: ManagerRepository) : ViewModel() {
    val message = MutableLiveData<String>()

    fun saveDepart(departmentName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.saveDepartment(
                DepartmentDTO(
                    departmentName
                )
            )
        }
        message.value = "Відділ додано до бази даних!"
    }
}
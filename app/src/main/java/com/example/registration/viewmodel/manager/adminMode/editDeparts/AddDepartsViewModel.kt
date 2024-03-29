package com.example.registration.viewmodel.manager.adminMode.editDeparts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.R
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddDepartsViewModel(private val managerRepository: ManagerRepository) : ViewModel() {
    val message = MutableLiveData<String>()

    private fun showInvalideMessage() {
        message.value = "Відділ з такою назвою вже існує!"
    }

    private fun showSuccessfulMessage() {
        message.value = "Відділ доданий успішно!"
    }

    fun saveDepart(departmentName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.saveDepartment(departmentName)
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    showSuccessfulMessage()
                }.onFailure {
                    showInvalideMessage()
                }
            }
        }
    }
}
package com.example.registration.ui.manager.adminMode.departments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
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
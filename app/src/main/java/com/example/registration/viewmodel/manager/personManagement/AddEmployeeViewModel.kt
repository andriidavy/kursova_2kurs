package com.example.registration.viewmodel.manager.personManagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.R
import com.example.registration.model.users.Employee
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEmployeeViewModel(private val managerRepository: ManagerRepository) : ViewModel() {

    val message = MutableLiveData<String>()

    private fun showInvalideMessage() {
        message.value = "Робітник з таким email вже існує!"
    }

    private fun showSuccessfulMessage() {
        message.value = "Реєстрація пройшла успішно!"
    }

    fun addEmployee(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.saveEmployee(name, surname, email, password)
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
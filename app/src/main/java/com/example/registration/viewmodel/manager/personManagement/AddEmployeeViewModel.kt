package com.example.registration.viewmodel.manager.personManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.Employee
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEmployeeViewModel(private val managerRepository: ManagerRepository) : ViewModel() {

    val message = MutableLiveData<String>()

    fun addEmployee(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.saveEmployee(
                Employee(
                    name, surname, email, password
                )
            )
        }
        message.value = "Робітника додано до бази даних!"
    }
}
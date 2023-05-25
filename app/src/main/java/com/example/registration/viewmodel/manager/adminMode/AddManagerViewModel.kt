package com.example.registration.viewmodel.manager.adminMode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.Manager
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddManagerViewModel(private val managerRepository: ManagerRepository) : ViewModel() {

    val message = MutableLiveData<String>()

    fun addManager(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.saveManager(
                Manager(
                    name, surname, email, password
                )
            )
        }
        message.value = "Менеджера додано до бази даних!"
    }
}
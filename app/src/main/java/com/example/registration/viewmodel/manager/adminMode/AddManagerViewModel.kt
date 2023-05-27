package com.example.registration.viewmodel.manager.adminMode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.Manager
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddManagerViewModel(private val managerRepository: ManagerRepository) : ViewModel() {

    val message = MutableLiveData<String>()

    private fun showInvalideMessage() {
        message.value = "Робітник з таким email вже існує!"
    }

    private fun showSuccessfulMessage() {
        message.value = "Реєстрація пройшла успішно!"
    }

    fun addManager(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.saveManager(name, surname, email, password)
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
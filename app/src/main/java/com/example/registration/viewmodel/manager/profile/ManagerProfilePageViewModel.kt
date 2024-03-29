package com.example.registration.viewmodel.manager.profile

import android.content.SharedPreferences
import android.os.Build.VERSION_CODES.N
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.CustomerProfileDTO
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagerProfilePageViewModel(private val managerRepository: ManagerRepository): ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _managerProfileDTO = MutableLiveData<ManagerProfileDTO>()
    val managerProfileDTO: LiveData<ManagerProfileDTO>
        get() = _managerProfileDTO

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val managerId: Int
        get() = sharedPreferences.getInt("managerId", 0)

    fun getManagerProfileById() : LiveData<ManagerProfileDTO> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getManagerProfile(managerId)
            withContext(Dispatchers.Main) {
                _managerProfileDTO.value = result
            }
        }
        return managerProfileDTO
    }

}
package com.example.registration.viewmodel.employee.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.database.employee.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeProfilePageViewModel(private val employeeRepository: EmployeeRepository): ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _employeeProfileDTO = MutableLiveData<EmployeeProfileDTO>()
    val employeeProfileDTO: LiveData<EmployeeProfileDTO>
        get() = _employeeProfileDTO

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val employeeId: Int
        get() = sharedPreferences.getInt("employeeId", 0)

    fun getEmployeeProfileById() : LiveData<EmployeeProfileDTO> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = employeeRepository.getEmployeeProfile(employeeId)
            withContext(Dispatchers.Main) {
                _employeeProfileDTO.value = result
            }
        }
        return employeeProfileDTO
    }
}
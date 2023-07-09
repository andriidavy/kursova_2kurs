package com.example.registration.viewmodel.employee.customsProcessed

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.database.employee.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeCustomsProcessedViewModel(private val employeeRepository: EmployeeRepository) :
    ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _customProcessedArray = MutableLiveData<List<CustomDTO>>()
    val customProcessedArray: LiveData<List<CustomDTO>>
        get() = _customProcessedArray

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val employeeId: Int
        get() = sharedPreferences.getInt("employeeId", 0)

    fun getProcessedCustomsForEmployee(): LiveData<List<CustomDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = employeeRepository.getProcessedCustomsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                _customProcessedArray.postValue(result)
            }
        }
        return customProcessedArray
    }

    fun setCustomSent(customId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.setCustomSent(customId)
            getProcessedCustomsForEmployee()
        }
    }
}
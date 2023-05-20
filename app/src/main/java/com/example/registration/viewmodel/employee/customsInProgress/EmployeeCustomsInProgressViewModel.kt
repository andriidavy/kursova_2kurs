package com.example.registration.viewmodel.employee.customsInProgress

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.databinding.FragmentEmployeeCustomInProgressBinding
import com.example.registration.model.custom.CustomDTO
import com.example.registration.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeCustomsInProgressViewModel(private val employeeRepository: EmployeeRepository) :ViewModel() {
private lateinit var sharedPreferences: SharedPreferences

    private val _customInProgressArray = MutableLiveData<List<CustomDTO>>()
    val customInProgressArray: LiveData<List<CustomDTO>>
        get() = _customInProgressArray

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val employeeId: Int
        get() = sharedPreferences.getInt("employeeId", 0)

    fun getInProgressCustomsForEmployee(): LiveData<List<CustomDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = employeeRepository.getProcessingCustomsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                _customInProgressArray.postValue(result)
            }
        }
        return customInProgressArray
    }
}
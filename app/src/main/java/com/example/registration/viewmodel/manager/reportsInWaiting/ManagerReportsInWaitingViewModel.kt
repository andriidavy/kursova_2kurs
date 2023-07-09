package com.example.registration.viewmodel.manager.reportsInWaiting

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.report.ReportDTO
import com.example.registration.database.manager.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagerReportsInWaitingViewModel(private val managerRepository: ManagerRepository) :
    ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _reportInWaitingArray = MutableLiveData<List<ReportDTO>>()
    val reportInWaitingArray: LiveData<List<ReportDTO>>
        get() = _reportInWaitingArray

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val managerId: Int
        get() = sharedPreferences.getInt("managerId", 0)

    fun getAllInWaitingReports(): LiveData<List<ReportDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllWaiting(managerId)
            withContext(Dispatchers.Main) {
                _reportInWaitingArray.postValue(result)
            }
        }
        return reportInWaitingArray
    }
}
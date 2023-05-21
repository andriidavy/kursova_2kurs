package com.example.registration.viewmodel.manager.reportsInWaiting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManagerReportsInWaitingDetailViewModel(private val managerRepository: ManagerRepository) :
    ViewModel() {

    fun setReportAccepted(reportId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.setReportAccepted(reportId)
        }
    }

    fun setReportRejected(reportId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.setReportRejected(reportId)
        }
    }
}
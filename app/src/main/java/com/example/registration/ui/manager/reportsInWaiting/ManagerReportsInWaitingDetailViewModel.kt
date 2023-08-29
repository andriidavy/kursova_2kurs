package com.example.registration.ui.manager.reportsInWaiting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerReportsInWaitingDetailViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
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
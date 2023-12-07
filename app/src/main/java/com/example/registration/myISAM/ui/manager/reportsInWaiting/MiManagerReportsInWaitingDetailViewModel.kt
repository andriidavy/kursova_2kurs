package com.example.registration.myISAM.ui.manager.reportsInWaiting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.manager.MiManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiManagerReportsInWaitingDetailViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    fun setReportAccepted(reportId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            miManagerRepository.setReportAccepted(reportId)
        }
    }

    fun setReportRejected(reportId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            miManagerRepository.setReportRejected(reportId)
        }
    }
}
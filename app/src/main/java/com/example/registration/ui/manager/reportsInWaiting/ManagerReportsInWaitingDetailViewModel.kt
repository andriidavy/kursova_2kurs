package com.example.registration.ui.manager.reportsInWaiting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerReportsInWaitingDetailViewModel @Inject constructor(
    private val managerRepository: ManagerRepository,
    datastoreRepository: DatastoreRepo
) :
    DataStoreViewModel(datastoreRepository) {

    private val token = "Bearer ${getUserToken()}"
    fun setReportAccepted(reportId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.setReportAccepted(token, reportId)
        }
    }

    fun setReportRejected(reportId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.setReportRejected(token, reportId)
        }
    }
}
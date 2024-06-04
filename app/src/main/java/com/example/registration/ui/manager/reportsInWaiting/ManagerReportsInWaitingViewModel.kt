package com.example.registration.ui.manager.reportsInWaiting

import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.report.ReportDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerReportsInWaitingViewModel @Inject constructor(
    private val managerRepository: ManagerRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _reportInWaitingArray = MutableStateFlow<List<ReportDTO>>(emptyList())
    val reportInWaitingArray: StateFlow<List<ReportDTO>>
        get() = _reportInWaitingArray

    private val _filteredReportArray = MutableStateFlow<List<ReportDTO>>(emptyList())
    val filteredReportArray: StateFlow<List<ReportDTO>>
        get() = _filteredReportArray

    private val managerId = getUserId()

    init {
        getAllInWaitingReports()
    }

    private fun getAllInWaitingReports() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllWaiting(managerId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _reportInWaitingArray.value = it
                    _filteredReportArray.value = it
                }
            }
        }
    }

    fun filterReportsByReportId(query: String) {
        viewModelScope.launch {
            val filteredReports = _reportInWaitingArray.value.filter {
                it.reportId.toString().startsWith(query)
            }
            _filteredReportArray.value = filteredReports
        }
    }
}
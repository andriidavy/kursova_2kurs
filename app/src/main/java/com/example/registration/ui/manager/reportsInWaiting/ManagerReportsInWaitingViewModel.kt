package com.example.registration.ui.manager.reportsInWaiting

import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.report.ReportDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private val _filteredReportIndexes = MutableStateFlow<List<Int>>(emptyList())
    val filteredReportIndexes: StateFlow<List<Int>>
        get() = _filteredReportIndexes

    private val managerId = getUserId()

    init {
        getAllInWaitingReports()
    }

    fun getAllInWaitingReports() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllWaiting(managerId)
            withContext(Dispatchers.Main) {
                result.collect {
                    delay(100)
                    _reportInWaitingArray.value = it
                    delay(100)
                    _filteredReportArray.value = it
                    delay(100)
                    _filteredReportIndexes.value = it.indices.toList()
                }
            }
        }
    }

    fun filterReportsByReportId(query: String) {
        viewModelScope.launch {
            val filteredReports = _reportInWaitingArray.value.filter {
                it.reportId.toString().startsWith(query)
            }
            val filteredIndexes = _reportInWaitingArray.value
                .mapIndexedNotNull { index, report ->
                    if (report.reportId.toString().startsWith(query)) index else null
                }

            _filteredReportArray.value = filteredReports
            _filteredReportIndexes.value = filteredIndexes
        }
    }
}
package com.example.registration.ui.employee.customsInProgress

import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CreatingReportForCustomViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val employeeId = getUserId()

    fun createReport(
        customId: Int,
        reportText: String
    ): Flow<Result<Unit>> {
        return employeeRepository.createReport(employeeId, customId, reportText)
    }
}
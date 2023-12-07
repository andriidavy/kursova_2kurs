package com.example.registration.myISAM.ui.employee.customsInProgress

import com.example.registration.database.myIsam.employee.MiEmployeeRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MiCreatingReportForCustomViewModel @Inject constructor(
    private val miEmployeeRepository: MiEmployeeRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val employeeId = getUserId()

    fun createReport(
        customId: Int,
        reportText: String
    ): Flow<Result<Unit>> {
        return miEmployeeRepository.createReport(employeeId, customId, reportText)
    }
}
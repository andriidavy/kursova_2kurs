package com.example.registration.ui.manager.createdCustoms

import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerCreatedCustomsPageViewModel @Inject constructor(
    private val managerRepository: ManagerRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _customCreatedArray = MutableStateFlow<List<CustomDTO>>(emptyList())
    val customCreatedArray: StateFlow<List<CustomDTO>>
        get() = _customCreatedArray
    private val managerId = getUserId()
    private val token = "Bearer ${getUserToken()}"

    init {
        viewModelScope.launch {
            delay(200)
            getCreatedCustomsForManager(0)
        }
    }

    var currentPage = 0

    private val pageSize = 10

    fun isLastPage(): Boolean {
        return _customCreatedArray.value.size < pageSize
    }

    fun getCreatedCustomsForManager(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllCustomsWithoutEmployee(token, managerId, page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect {
                    _customCreatedArray.value = it
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLastPage()) {
            currentPage += 1
            viewModelScope.launch {
                delay(100)
                getCreatedCustomsForManager(currentPage)

            }
        }
    }

    fun loadPreviousPage() {
        if (currentPage > 0) currentPage -= 1 else 0
        viewModelScope.launch {
            delay(100)
            getCreatedCustomsForManager(currentPage)
        }
    }
}
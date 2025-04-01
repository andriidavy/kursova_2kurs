package com.example.registration.ui.manager.allCustoms

import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.custom.CustomDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerAllCustomsPageViewModel @Inject constructor(
    private val managerRepository: ManagerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val token = "Bearer ${getUserToken()}"
    private val _customAllArray = MutableStateFlow<List<CustomDTO>>(emptyList())
    private val managerId = getUserId()
    val customAllArray: StateFlow<List<CustomDTO>>
        get() = _customAllArray

    var currentPage = 0
    var chooseNum = 0

    private val pageSize = 10

    init {
        viewModelScope.launch {
            delay(200)
            getAllCustomsPage(0)
        }
    }

    fun isLastPage(): Boolean {
       // return _customAllArray.value.size < pageSize
        return false
    }

    fun getAllCustomsPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllCustoms(token, page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect { list ->
                    _customAllArray.value = list.map { custom ->
                        custom.copyWithUpdatedChatStatus(false)
                    }
                }
            }
        }
    }

    fun getAllCustomsWithMessagePage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                managerRepository.getAllCustomsWithMessage(token, managerId, page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect { list ->
                    _customAllArray.value = list.map { custom ->
                        custom.copyWithUpdatedChatStatus(true)
                    }
                }
            }
        }
    }

    fun getAllCustomsWithDepartmentPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                managerRepository.getAllCustomsWithDepartment(
                    token,
                    managerId,
                    page,
                    pageSize
                )
            withContext(Dispatchers.Main) {
                result.collect { list ->
                    _customAllArray.value = list.map { custom ->
                        custom.copyWithUpdatedChatStatus(true)
                    }
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLastPage()) {
            currentPage += 1
            viewModelScope.launch {
                delay(100)
                when (chooseNum) {
                    0 -> {
                        getAllCustomsPage(currentPage)
                    }

                    1 -> {
                        getAllCustomsWithDepartmentPage(currentPage)
                    }

                    2 -> {
                        getAllCustomsWithMessagePage(currentPage)
                    }
                }
            }
        }
    }

    fun loadPreviousPage() {
        if (currentPage > 0) currentPage -= 1 else 0
        viewModelScope.launch {
            delay(100)
            when (chooseNum) {
                0 -> {
                    getAllCustomsPage(currentPage)
                }

                1 -> {
                    getAllCustomsWithDepartmentPage(currentPage)
                }

                2 -> {
                    getAllCustomsWithMessagePage(currentPage)
                }
            }
        }
    }

    fun searchCustomById(customId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.searchCustomById(token, customId)
            withContext(Dispatchers.Main) {
                result.collect { result ->
                    result.onSuccess { custom ->
                        _customAllArray.value = listOf(custom)
                    }
                    result.onFailure {
                        _customAllArray.value = emptyList()
                    }
                }
            }
        }
    }
}
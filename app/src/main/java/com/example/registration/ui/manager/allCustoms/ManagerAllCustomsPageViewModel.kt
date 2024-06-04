package com.example.registration.ui.manager.allCustoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerAllCustomsPageViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    private val _customAllArray = MutableStateFlow<List<CustomDTO>>(emptyList())
    val customAllArray: StateFlow<List<CustomDTO>>
        get() = _customAllArray

    var currentPage = 0

    private val pageSize = 10

    init {
        viewModelScope.launch {
            delay(200)
            getAllCustomsPage(0)
        }
    }

    fun isLastPage(): Boolean {
        return _customAllArray.value.size < pageSize
    }

    fun getAllCustomsPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllCustoms(page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect {
                    _customAllArray.value = it
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLastPage()) {
            currentPage += 1
            viewModelScope.launch {
                delay(100)
                getAllCustomsPage(currentPage)

            }
        }
    }

    fun loadPreviousPage() {
        if (currentPage > 0) currentPage -= 1 else 0
        viewModelScope.launch {
            delay(100)
            getAllCustomsPage(currentPage)
        }
    }

    fun searchCustomById(customId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.searchCustomById(customId)
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
package com.example.registration.ui.manager.allCustoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.global.ToastObj
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
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

    init {
        getAllCustomsForManager()
    }

    fun getAllCustomsForManager() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllCustoms()
            withContext(Dispatchers.Main) {
                result.collect {
                    _customAllArray.value = it
                }
            }
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
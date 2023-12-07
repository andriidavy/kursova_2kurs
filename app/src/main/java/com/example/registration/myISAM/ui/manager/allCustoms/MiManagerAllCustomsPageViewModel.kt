package com.example.registration.myISAM.ui.manager.allCustoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.manager.MiManagerRepository
import com.example.registration.model.custom.CustomDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiManagerAllCustomsPageViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    private val _customAllArray = MutableStateFlow<List<CustomDTO>>(emptyList())
    val customAllArray: StateFlow<List<CustomDTO>>
        get() = _customAllArray

    init {
        getAllCustomsForManager()
    }

    fun getAllCustomsForManager() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miManagerRepository.getAllCustoms()
            withContext(Dispatchers.Main) {
                result.collect {
                    _customAllArray.value = it
                }
            }
        }
    }

    fun searchCustomById(customId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miManagerRepository.searchCustomById(customId)
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
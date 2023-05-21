package com.example.registration.viewmodel.manager.allCustoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagerAllCustomsPageViewModel(private val managerRepository: ManagerRepository): ViewModel() {

    private val _customAllArray = MutableLiveData<List<CustomDTO>>()
    val customAllArray: LiveData<List<CustomDTO>>
        get() = _customAllArray



    fun getAllCustomsForManager(): LiveData<List<CustomDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllCustoms()
            withContext(Dispatchers.Main) {
                _customAllArray.postValue(result)
            }
        }
        return customAllArray
    }

}
package com.example.registration.viewmodel.manager.createdCustoms

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagerCreatedCustomsPageViewModel(private val managerRepository: ManagerRepository): ViewModel()  {

    private val _customCreatedArray = MutableLiveData<List<CustomDTO>>()
    val customCreatedArray: LiveData<List<CustomDTO>>
        get() = _customCreatedArray



    fun getCreatedCustomsForManager(): LiveData<List<CustomDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllCreatedCustoms()
            withContext(Dispatchers.Main) {
                _customCreatedArray.postValue(result)
            }
        }
        return customCreatedArray
    }

}
package com.example.registration.viewmodel.manager.adminMode

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.database.manager.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditManagerViewModel(private val managerRepository: ManagerRepository): ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _managerAllArray = MutableLiveData<List<ManagerProfileDTO>>()
    val managerAllArray: LiveData<List<ManagerProfileDTO>>
        get() = _managerAllArray

    fun getAllManagersProfileDTO(): LiveData<List<ManagerProfileDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllManagersProfileDTO()
            withContext(Dispatchers.Main) {
                _managerAllArray.postValue(result)
            }
        }
        return managerAllArray
    }

    fun deleteManagerById(managerId : Int) {
        viewModelScope.launch(Dispatchers.IO){
            managerRepository.deleteManagerById(managerId);
            getAllManagersProfileDTO()
        }
    }
}
package com.example.registration.viewmodel.manager.adminMode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllDepartViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    private val _departNonForManagerArray = MutableLiveData<List<DepartmentDTO>>()
    val departNonForManagerArray: LiveData<List<DepartmentDTO>>
        get() = _departNonForManagerArray

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun getDepartmentsWithoutManager(managerId: Int): LiveData<List<DepartmentDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getDepartmentsWithoutManager(managerId)
            withContext(Dispatchers.Main) {
                _departNonForManagerArray.postValue(result)
            }
        }
        return departNonForManagerArray
    }

    fun assignDepartmentToManager(managerId: Int, departmentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.assignDepartmentToManager(managerId, departmentId)
        }
        _message.value = "Відділ призначено менеджеру успішно"
        getDepartmentsWithoutManager(managerId)
    }
}
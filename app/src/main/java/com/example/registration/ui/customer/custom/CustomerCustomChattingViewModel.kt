package com.example.registration.ui.customer.custom

import androidx.lifecycle.viewModelScope
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.message.MessageDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerCustomChattingViewModel @Inject constructor(
    private val customerRepository: CustomerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository){

    private val _messageDTOArray = MutableStateFlow<List<MessageDTO>>(emptyList())
    val messageDTOArray: StateFlow<List<MessageDTO>>
        get() = _messageDTOArray

    private val token = "Bearer ${getUserToken()}"

    fun getMessageForCustom(customId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMessageForCustom(token, customId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _messageDTOArray.value = it
                }
            }
        }
    }
}
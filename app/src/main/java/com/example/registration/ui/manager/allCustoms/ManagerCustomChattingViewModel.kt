package com.example.registration.ui.manager.allCustoms

import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.message.MessageDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerCustomChattingViewModel @Inject constructor(
    private val managerRepository: ManagerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _messageDTOArray = MutableStateFlow<List<MessageDTO>>(emptyList())
    val messageDTOArray: StateFlow<List<MessageDTO>>
        get() = _messageDTOArray

    private val managerId = getUserId()

    private val token = "Bearer ${getUserToken()}"

    fun getMessageForCustom(customId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getMessageForCustom(token, customId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _messageDTOArray.value = it
                }
            }
        }
    }

    fun pollMessages(customId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val result = managerRepository.getMessageForCustom(token, customId)
                withContext(Dispatchers.Main) {
                    result.collect {
                        _messageDTOArray.value = it
                    }
                }
                delay(3000) // Запрашиваем сообщения каждые 5 секунд
            }
        }
    }

    fun sendMessageByManager(customId: Int, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.sendMessageByManager(token, customId, managerId, text)
        }
    }

    fun closeChat(customId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.closeChat(token, customId)

        }
    }
}
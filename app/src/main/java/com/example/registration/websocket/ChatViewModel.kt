package com.example.registration.websocket

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val webSocketClient: ChatWebSocketClient
) : ViewModel() {

    fun connectWebSocket() {
        webSocketClient.connect()
    }

    fun sendMessage(message: String) {
        webSocketClient.send(message)
    }

    override fun onCleared() {
        webSocketClient.close()
        super.onCleared()
    }
}
package com.example.registration.websocket

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class ChatWebSocketClient(serverUri: URI) : WebSocketClient(serverUri) {


    private var messageListener: ((String) -> Unit)? = null

    fun setMessageListener(listener: (String) -> Unit) {
        messageListener = listener
    }

    override fun onOpen(handshakedata: ServerHandshake?) {
        println("WebSocket подключен")
    }

    override fun onMessage(message: String?) {
        println("Получено сообщение: $message")
        message?.let { messageListener?.invoke(it) }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("WebSocket закрыт: $reason")
    }

    override fun onError(ex: Exception?) {
        println("Ошибка WebSocket: ${ex?.message}")
    }
}
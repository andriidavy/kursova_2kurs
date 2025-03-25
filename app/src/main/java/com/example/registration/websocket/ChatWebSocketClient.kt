package com.example.registration.websocket

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class ChatWebSocketClient(serverUri: URI) : WebSocketClient(serverUri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        println("WebSocket подключен")
    }

    override fun onMessage(message: String?) {
        println("Получено сообщение: $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("WebSocket закрыт: $reason")
    }

    override fun onError(ex: Exception?) {
        println("Ошибка WebSocket: ${ex?.message}")
    }
}
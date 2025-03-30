//package com.example.registration.websocket
//
//import android.util.Log
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.WebSocket
//import okhttp3.WebSocketListener
//import okio.ByteString
//
//class StompWebSocketClient(private val serverUrl: String) {
//
//    private var webSocket: WebSocket? = null
//    private val client = OkHttpClient()
//
//    fun connect() {
//        val request = Request.Builder().url(serverUrl).build()
//        webSocket = client.newWebSocket(request, object : WebSocketListener() {
//            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
//                Log.d("WebSocket", "Соединение установлено")
//                send("CONNECT\n\n\u0000") // STOMP-протокол требует отправки CONNECT
//            }
//
//            override fun onMessage(webSocket: WebSocket, text: String) {
//                Log.d("WebSocket", "Сообщение: $text")
//            }
//
//            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//                Log.d("WebSocket", "Сообщение (бинарное): $bytes")
//            }
//
//            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//                Log.d("WebSocket", "Закрывается: $reason")
//                webSocket.close(1000, null)
//            }
//
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
//                Log.e("WebSocket", "Ошибка: ${t.message}")
//            }
//        })
//    }
//
//    fun send(message: String) {
//        webSocket?.send(message) ?: Log.e("WebSocket", "Соединение не установлено")
//    }
//
//    fun disconnect() {
//        webSocket?.close(1000, "Отключение")
//    }
//}
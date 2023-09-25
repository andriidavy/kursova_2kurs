package com.example.registration.ui.start.loginscreens

data class Credentials(var login: String = "", var password: String = "") {
    fun isEmpty(): Boolean {
        return login.isBlank() && password.isBlank()
    }
}
package com.example.registration.ui.start.login

data class Credentials(var email: String = "", var password: String = "") {
    fun isEmpty(): Boolean {
        return email.isBlank() || password.isBlank()
    }
}
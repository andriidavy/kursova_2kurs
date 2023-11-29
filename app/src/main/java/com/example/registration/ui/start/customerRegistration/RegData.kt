package com.example.registration.ui.start.customerRegistration

data class RegData(
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = "",
    var confPassword: String = ""
) {
    fun isEmpty(): Boolean {
        return name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank() || confPassword.isBlank()
    }

    fun isPasswordConfirmed(): Boolean{
        return password == confPassword
    }
}
package com.example.registration.global

import com.auth0.android.jwt.JWT

object JwtDecode {
    fun decodeJwtToken(token: String) : LoginResponse {
        // Создаем объект JWT, передавая в него токен
        val jwt = JWT(token)

        // Извлечение полезной информации (claims) из токена
        val id = jwt.getClaim("id").asInt() ?: 0
        val email = jwt.getClaim("sub").asString() ?:"Unknown" // или "username" в зависимости от того, что хранится в subject
        //val role = jwt.getClaim("role").asString() // Вытягиваем роль, если она присутствует в токене
        val expiration = jwt.expiresAt // Дата истечения срока действия токена
        return LoginResponse(id, email, expiration)
    }
}
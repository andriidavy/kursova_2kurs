package com.example.registration.datastore

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.registration.datastore.Constants.USER_ID
import com.example.registration.datastore.Constants.USER_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
open class DataStoreViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepo
) : ViewModel() {

    fun storeUserId(value: Int) = runBlocking {
        datastoreRepository.putInt(USER_ID, value)
    }

    fun getUserId(): Int = runBlocking {
        datastoreRepository.getInt(USER_ID)!!
    }

    fun storeUserToken(value: String) = runBlocking {
        datastoreRepository.putString(USER_TOKEN, value)
    }

    fun getUserToken(): String = runBlocking {
        datastoreRepository.getString(USER_TOKEN)?: "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9NQU5BR0VSIiwiaWQiOjQsInN1YiI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzQzNDQ2ODY5LCJleHAiOjE3NDM0NTA0Njl9.gnmYK1-yH15J1h8nDN_ntC0vbebQ0yCMWZci9IMhkoE"
    }

    fun clearAllPreferences() = runBlocking {
        datastoreRepository.clearStringPreferences(USER_TOKEN)
        datastoreRepository.clearIntPreferences(USER_ID)
    }

    private var tokenExpirationTimer: CountDownTimer? = null

    // Запуск таймера для моніторингу закінчення токена
    fun startTokenExpirationTimer(expirationDate: Date, onTokenExpired: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        val expirationTime = expirationDate.time
        val remainingTime = expirationTime - currentTime

        if (remainingTime <= 0) {
            onTokenExpired()
            return
        }

        tokenExpirationTimer?.cancel()  // Зупиняємо попередній таймер, якщо він був

        tokenExpirationTimer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                onTokenExpired()  // Викликати дію, коли токен закінчиться
            }
        }.start()
    }

    fun cancelTokenExpirationTimer() {
        tokenExpirationTimer?.cancel()
    }

}
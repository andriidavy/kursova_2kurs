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
        datastoreRepository.getString(USER_TOKEN)!!
    }

    fun clearAllPreferences() = runBlocking {
        datastoreRepository.clearStringPreferences(USER_TOKEN)
        datastoreRepository.clearIntPreferences(USER_ID)
    }

    private var tokenExpirationTimer: CountDownTimer? = null

    // Запуск таймера для моніторингу закінчення токена
    fun startTokenExpirationTimer(expirationDate: Date, onTokenExpired: () -> Unit) {
        val currentTime = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)
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
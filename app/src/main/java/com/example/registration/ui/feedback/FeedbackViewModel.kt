package com.example.registration.ui.feedback

import com.example.registration.database.backendless.notification.NotificationRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.notification.feedback.EmailRequest
import com.example.registration.model.notification.feedback.PartsOfBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    fun sendEmail(subject: String, message: String): Flow<Result<Unit>> {
        val emailRequest = EmailRequest(
            subject = subject,
            bodyParts = PartsOfBody(textMessage = message),
            to = listOf("dav1dcrpt09@gmail.com")
        )
        val userToken = getUser()?.userToken
        return notificationRepository.sendEmail(userToken, emailRequest)
    }
}
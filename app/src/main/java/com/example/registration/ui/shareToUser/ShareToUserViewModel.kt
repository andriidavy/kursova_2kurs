package com.example.registration.ui.shareToUser

import com.example.registration.database.backendless.files.FilesRepository
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.data.GuestUserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ShareToUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val filesRepository: FilesRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    fun shareFile(
        guestUserName: String,
        file: MultipartBody.Part,
        fileName: String
    ): Flow<Result<String>> {
        val path = "sharedWithMe"
        val finalFileNane = "$fileName.txt"
        return filesRepository.shareFile(guestUserName, file, finalFileNane, userToken, path)
    }

    fun getUserByName(userName: String): Flow<Result<List<GuestUserDTO>>> {
        return userRepository.getUserByName(userName)
    }
}
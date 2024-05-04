package com.example.registration.database.backendless.files

import com.example.registration.model.directoryItem.ServerItem
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FilesApi {
    @POST("files/{userName}/{folderName}")
    suspend fun createFolder(
        @Path("userName") userName: String,
        @Path("folderName") folderName: String
    )

    @DELETE("files/{filePath}")
    suspend fun deleteFile(
        @Path("filePath") filePath: String
    )

    @GET("files/{userName}/{path}")
    suspend fun getFilesFromServerFolder(
        @Header("user-token") userToken: String,
        @Path("userName") userName: String,
        @Path("path") path: String,
        @Query("sub") recursive: Boolean = false
    ): List<ServerItem>
}
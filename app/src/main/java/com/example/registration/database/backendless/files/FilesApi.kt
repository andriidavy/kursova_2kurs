package com.example.registration.database.backendless.files

import com.example.registration.model.directoryItem.ServerItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface FilesApi {
    @POST("files/{userName}/{filePath}/{newFolderName}")
    suspend fun createFolder(
        @Path("userName") userName: String,
        @Path("filePath") filePath: String,
        @Path("newFolderName") newFolderName: String
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

    @GET("https://backendlessappcontent.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/files/{path}")
    @Streaming
    suspend fun downloadFile(
        @Path("path") path: String,
        @Header("user-token") userToken: String
    ): ResponseBody

    @POST("https://backendlessappcontent.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/files/{userName}/{path}/{filename}")
    @Multipart
    suspend fun uploadFile(
        @Path("userName") userName: String,
        @Path("path") path: String,
        @Path("filename") filename: String,
        @Header("user-token") userToken: String,
        @Part file: MultipartBody.Part,
        @Query("overwrite") overwrite: Boolean = false
    ): Response<ResponseBody>
}
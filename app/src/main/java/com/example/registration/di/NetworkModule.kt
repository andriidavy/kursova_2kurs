package com.example.registration.di

import com.example.registration.database.customer.CustomerApi
import com.example.registration.database.employee.EmployeeApi
import com.example.registration.database.manager.ManagerApi
import com.example.registration.database.backendless.user.UserApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideCustomerApi(retrofit: Retrofit): CustomerApi = retrofit.create(CustomerApi::class.java)

    @Provides
    @Singleton
    fun provideEmployeeApi(retrofit: Retrofit): EmployeeApi = retrofit.create(EmployeeApi::class.java)

    @Provides
    @Singleton
    fun provideManagerApi(retrofit: Retrofit): ManagerApi = retrofit.create(ManagerApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
}
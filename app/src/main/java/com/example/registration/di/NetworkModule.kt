package com.example.registration.di

import com.example.registration.database.customer.CustomerApi
import com.example.registration.database.employee.EmployeeApi
import com.example.registration.database.manager.ManagerApi
import com.example.registration.database.myIsam.customer.MiCustomerApi
import com.example.registration.database.myIsam.employee.MiEmployeeApi
import com.example.registration.database.myIsam.manager.MiManagerApi
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
            .baseUrl("http://192.168.43.122:5001")
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
    fun provideMiCustomerApi(retrofit: Retrofit): MiCustomerApi = retrofit.create(MiCustomerApi::class.java)

    @Provides
    @Singleton
    fun provideMiEmployeeApi(retrofit: Retrofit): MiEmployeeApi = retrofit.create(MiEmployeeApi::class.java)

    @Provides
    @Singleton
    fun provideMiManagerApi(retrofit: Retrofit): MiManagerApi = retrofit.create(MiManagerApi::class.java)
}
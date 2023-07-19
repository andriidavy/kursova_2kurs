package com.example.registration.di

import android.content.Context
import com.example.registration.datastore.DataStoreRepoImpl
import com.example.registration.datastore.DatastoreRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesDatastoreRepo(
        @ApplicationContext context: Context
    ): DatastoreRepo = DataStoreRepoImpl(context)
}
package com.example.linearandconstraintlayouts.di.module

import android.content.Context
import androidx.room.Room
import com.example.linearandconstraintlayouts.api.dogapi.*
import com.example.linearandconstraintlayouts.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "db").fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideDogApiInterface() = DogApiHTTPClient.getInstance()

    @Provides
    @Singleton
    fun provideDogCdnInterface() = DogCdnHTTPClient.getInstance()

    @Provides
    @Singleton
    fun provideDogDataSource() = DogDataSource(client = provideDogApiInterface())
}
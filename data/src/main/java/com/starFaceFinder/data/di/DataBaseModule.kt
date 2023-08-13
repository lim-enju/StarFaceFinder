package com.starFaceFinder.data.di

import android.content.Context
import androidx.room.Room
import com.starFaceFinder.data.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "history_db"
        ).build()

    @Provides
    fun provideFaceInfoDao(appDatabase: AppDatabase) = appDatabase.faceInfoDao()

    @Provides
    fun provideSimilarFacesDao(appDatabase: AppDatabase) = appDatabase.similarFacesDao()
}
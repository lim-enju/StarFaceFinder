package com.starFaceFinder.data.di

import com.starFaceFinder.data.source.local.HistoryLocalDataSource
import com.starFaceFinder.data.source.local.IHistoryLocalDataSource
import com.starFaceFinder.data.source.network.ISearchRemoteDataSource
import com.starFaceFinder.data.source.network.SearchRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindSearchRemoteDataSource(searchRemoteDataSource: SearchRemoteDataSource): ISearchRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindHistoryLocalDataSource(historyLocalDataSource: HistoryLocalDataSource): IHistoryLocalDataSource
}
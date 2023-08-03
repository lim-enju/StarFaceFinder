package com.starFaceFinder.data.di

import com.starFaceFinder.data.BuildConfig
import com.starFaceFinder.data.service.FindFaceService
import com.starFaceFinder.data.source.SearchRepository
import com.starFaceFinder.data.source.network.SearchDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.SERVICE_BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): FindFaceService {
        return retrofit.create(FindFaceService::class.java)
    }

    @Singleton
    @Provides
    fun provideFindFaceDataSource(service:FindFaceService) = SearchDataSource(service)

    @Singleton
    @Provides
    fun provideFindFaceRepository(datasource:SearchDataSource) = SearchRepository(datasource)
}
package com.starFaceFinder.data.service

import com.starFaceFinder.data.BuildConfig
import com.starFaceFinder.data.model.FindFaceResponse
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface FindFaceService {
    @Multipart
    @POST("vision/celebrity")
    suspend fun findFace(
        @Part name: MultipartBody.Part,
        @Part filename: MultipartBody.Part,
        @Part image: MultipartBody.Part,
        @Header("X-Naver-Client-Id") naverClientId: String = BuildConfig.NaverClientId,
        @Header("X-Naver-Client-secret") naverClientSecret: String = BuildConfig.NaverClientSecret
    ): FindFaceResponse
}
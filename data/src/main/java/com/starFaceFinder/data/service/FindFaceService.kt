package com.starFaceFinder.data.service

import com.starFaceFinder.data.BuildConfig
import com.starFaceFinder.data.model.response.SearchedFaceInfoResponse
import com.starFaceFinder.data.model.response.SearchedSimilarFaceResponse
import com.starFaceFinder.data.model.response.SearchedImageResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface FindFaceService {
    @Multipart
    @POST("vision/celebrity")
    suspend fun searchSimilarFace(
        @Part name: MultipartBody.Part,
        @Part filename: MultipartBody.Part,
        @Part image: MultipartBody.Part,
        @Header("X-Naver-Client-Id") naverClientId: String = BuildConfig.NaverClientId,
        @Header("X-Naver-Client-secret") naverClientSecret: String = BuildConfig.NaverClientSecret
    ): SearchedSimilarFaceResponse

    @GET("search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Header("X-Naver-Client-Id") naverClientId: String = BuildConfig.NaverClientId,
        @Header("X-Naver-Client-secret") naverClientSecret: String = BuildConfig.NaverClientSecret
    ): SearchedImageResponse

    //TODO:: header 위치 이동
    @Multipart
    @POST("vision/face")
    suspend fun searchFaceInfo(
        @Part name: MultipartBody.Part,
        @Part filename: MultipartBody.Part,
        @Part image: MultipartBody.Part,
        @Header("X-Naver-Client-Id") naverClientId: String = BuildConfig.NaverClientId,
        @Header("X-Naver-Client-secret") naverClientSecret: String = BuildConfig.NaverClientSecret,
    ): Result<SearchedFaceInfoResponse>
}
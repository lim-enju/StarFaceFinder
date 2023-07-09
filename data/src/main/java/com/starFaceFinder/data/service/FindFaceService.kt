package com.starFaceFinder.data.service

import com.starFaceFinder.data.model.Face
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface FindFaceService {
    @Multipart
    @POST("vision/celebrity")
    suspend fun findFace(
        @Part name: MultipartBody.Part,
        @Part filename: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): Face
}
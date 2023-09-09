package com.starFaceFinder.data.source.network

import com.starFaceFinder.data.model.response.SearchedFaceInfoResponse
import com.starFaceFinder.data.model.response.SearchedSimilarFaceResponse
import com.starFaceFinder.data.model.response.SearchedImageResponse
import java.io.File

interface ISearchRemoteDataSource {
    suspend fun searchSimilarFace(
        name: String,
        filename: String,
        image: File
    ): Result<SearchedSimilarFaceResponse>

    suspend fun searchImage(query: String)
    : Result<SearchedImageResponse>

    suspend fun searchFaceInfo(
        name: String,
        filename: String,
        image: File
    ): Result<SearchedFaceInfoResponse>
}
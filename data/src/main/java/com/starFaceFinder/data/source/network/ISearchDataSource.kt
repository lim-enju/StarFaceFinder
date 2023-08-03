package com.starFaceFinder.data.source.network

import com.starFaceFinder.data.model.response.FindFaceResponse
import com.starFaceFinder.data.model.response.SearchedImageResponse
import java.io.File

interface ISearchDataSource {
    suspend fun findFace(name: String, filename: String, image: File): FindFaceResponse

    suspend fun searchImage(query: String): SearchedImageResponse
}
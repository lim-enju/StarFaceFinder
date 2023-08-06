package com.starFaceFinder.data.source.network

import com.starFaceFinder.data.model.response.SearchedFaceInfoResponse
import com.starFaceFinder.data.model.response.SearchedSimilarFaceResponse
import com.starFaceFinder.data.model.response.SearchedImageResponse
import com.starFaceFinder.data.service.FindFaceService
import com.starFaceFinder.data.util.getBody
import java.io.File

class SearchDataSource constructor(
    private val findFaceService: FindFaceService
) : ISearchDataSource {
    override suspend fun searchSimilarFace(name: String, filename: String, image: File): SearchedSimilarFaceResponse =
        findFaceService.searchSimilarFace(
            name = name.getBody("name"),
            filename = filename.getBody("filename"),
            image = image.getBody("image")
        )

    override suspend fun searchImage(query: String): SearchedImageResponse =
        findFaceService.searchImage(query)

    override suspend fun searchFaceInfo(
        name: String,
        filename: String,
        image: File
    ): SearchedFaceInfoResponse =
        findFaceService.searchFaceInfo(
            name = name.getBody("name"),
            filename = filename.getBody("filename"),
            image = image.getBody("image")
        )
}
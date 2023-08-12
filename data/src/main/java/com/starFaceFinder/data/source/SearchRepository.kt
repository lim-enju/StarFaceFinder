package com.starFaceFinder.data.source

import com.starFaceFinder.data.source.network.ISearchDataSource
import java.io.File

class SearchRepository constructor(
    private val searchDataSource: ISearchDataSource,
){
    suspend fun searchSimilarFace(
        name: String,
        filename: String,
        image: File
    ) = kotlin.runCatching {
        searchDataSource.searchSimilarFace(name, filename, image)
    }

    suspend fun searchImage(
        query: String
    ) = kotlin.runCatching {
        searchDataSource.searchImage(query)
    }

    suspend fun searchFaceInfo(
        name: String,
        filename: String,
        image: File
    ) = kotlin.runCatching {
        searchDataSource.searchFaceInfo(name, filename, image)
            .toFaceInfo()
    }
}
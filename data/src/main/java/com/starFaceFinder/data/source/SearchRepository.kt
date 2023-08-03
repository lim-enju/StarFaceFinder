package com.starFaceFinder.data.source

import com.starFaceFinder.data.source.network.ISearchDataSource
import java.io.File

class SearchRepository constructor(
    private val searchDataSource: ISearchDataSource,
){
    suspend fun searchFaceResult(
        name: String,
        filename: String,
        image: File
    ) = kotlin.runCatching {
        searchDataSource.findFace(name, filename, image)
    }

    suspend fun searchImage(
        query: String
    ) = kotlin.runCatching {
        searchDataSource.searchImage(query)
    }
}
package com.starFaceFinder.data.source

import com.starFaceFinder.data.source.network.IFindFaceDataSource
import kotlinx.coroutines.flow.flow
import java.io.File

class FindFaceRepository constructor(
    private val findFaceDataSource: IFindFaceDataSource
){
    suspend fun getFindFaceResult(
        name: String,
        filename: String,
        image: File
    ) = kotlin.runCatching {
        findFaceDataSource.findFace(name, filename, image)
    }
}
package com.starFaceFinder.data.source

import com.starFaceFinder.data.model.request.FindFaceRequest
import com.starFaceFinder.data.source.network.FindFaceDataSource
import com.starFaceFinder.data.source.network.IFindFaceDataSource

class FindFaceRepository constructor(
    private val findFaceDataSource: FindFaceDataSource
){
    suspend fun getFindFaceResult(request: FindFaceRequest)
        = findFaceDataSource.findFace(request)
}
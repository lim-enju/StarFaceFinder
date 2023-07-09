package com.starFaceFinder.data.source.network

import com.starFaceFinder.data.model.Face
import com.starFaceFinder.data.model.request.FindFaceRequest

interface IFindFaceDataSource {
    suspend fun findFace(request: FindFaceRequest): Face
}
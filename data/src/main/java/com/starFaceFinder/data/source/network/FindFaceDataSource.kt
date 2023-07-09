package com.starFaceFinder.data.source.network

import com.starFaceFinder.data.model.Face
import com.starFaceFinder.data.model.request.FindFaceRequest
import com.starFaceFinder.data.service.FindFaceService
import com.starFaceFinder.data.util.getBody

class FindFaceDataSource constructor(
    private val findFaceService: FindFaceService
) : IFindFaceDataSource {
    //TDOO:: Exception 처리
    override suspend fun findFace(request: FindFaceRequest): Face =
        findFaceService.findFace(
            name = request.name.getBody("name")!!,
            filename = request.image.getBody("filename")!!,
            image = request.image.getBody("image")!!
        )

}
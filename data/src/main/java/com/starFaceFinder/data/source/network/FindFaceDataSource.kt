package com.starFaceFinder.data.source.network

import com.starFaceFinder.data.model.FindFaceResponse
import com.starFaceFinder.data.service.FindFaceService
import com.starFaceFinder.data.util.getBody
import java.io.File

class FindFaceDataSource constructor(
    private val findFaceService: FindFaceService
) : IFindFaceDataSource {
    //TDOO:: Exception 처리
    override suspend fun findFace(name: String, filename: String, image: File): FindFaceResponse =
        findFaceService.findFace(
            name = name.getBody("name")!!,
            filename = filename.getBody("filename")!!,
            image = image.getBody("image")!!
        )
}
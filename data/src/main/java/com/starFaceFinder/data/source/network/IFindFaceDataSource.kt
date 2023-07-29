package com.starFaceFinder.data.source.network

import com.starFaceFinder.data.model.FindFaceResponse
import java.io.File

interface IFindFaceDataSource {
    suspend fun findFace(name: String, filename: String, image: File): FindFaceResponse
}
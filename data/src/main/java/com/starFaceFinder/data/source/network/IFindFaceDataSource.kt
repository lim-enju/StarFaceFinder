package com.starFaceFinder.data.source.network

import com.starFaceFinder.data.model.Face
import java.io.File

interface IFindFaceDataSource {
    suspend fun findFace(name: String, filename: String, image: File): Face
}
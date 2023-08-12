package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.response.Faces
import com.starFaceFinder.data.source.SearchRepository
import java.io.File
import javax.inject.Inject

class SearchFaceInfoUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun invoke(image: File): Result<FaceInfo?>{
        val faceInfoResult = searchRepository.searchFaceInfo(image.name, image.name, image)
        val faceInfo = faceInfoResult.getOrElse { return Result.failure(Throwable()) }
        return Result.success(faceInfo)
    }
}
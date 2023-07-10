package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.source.FindFaceRepository
import java.io.File
import javax.inject.Inject

class FindFaceUseCase @Inject constructor(
    private val findFaceRepository: FindFaceRepository
) {
    suspend fun invoke(
        image: File
    ) = findFaceRepository.getFindFaceResult(image.name, image.name, image)
}
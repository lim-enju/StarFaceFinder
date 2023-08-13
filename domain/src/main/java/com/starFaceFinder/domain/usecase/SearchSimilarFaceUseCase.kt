package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.data.source.SearchRepository
import java.io.File
import javax.inject.Inject

class SearchSimilarFaceUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    suspend fun invoke(
        fid: Long,
        image: File
    ): Result<List<SimilarFace>> =
        searchRepository.searchSimilarFace(fid, image.name, image.name, image)
}
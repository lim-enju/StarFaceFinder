package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.model.SimilarFaces
import com.starFaceFinder.data.source.SearchRepository
import java.io.File
import javax.inject.Inject

class SearchSimilarFaceUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun invoke(
        image: File
    ): Result<List<SimilarFaces>> {
        //유사한 유명인 검색
        val searchedFacesResult = searchRepository.searchFaceResult(image.name, image.name, image)
        val searchedFaces = searchedFacesResult.getOrElse { return Result.failure(it) }

        val similarFaces = searchedFaces.faces.map { face ->
            val name = face.celebrity?.value?: return@map null
            val confidence = face.celebrity?.confidence?: return@map null

            //연예인 사진 검색
            val searchedImagesResult = searchRepository.searchImage(name)
            val searchedImages = searchedImagesResult.getOrElse { return Result.failure(it) }
            val firstImage = searchedImages.items.firstOrNull()?: return@map null

            SimilarFaces(
                name,
                confidence,
                firstImage.link,
                firstImage.thumbnail,
                firstImage.sizeheight,
                firstImage.sizewidth
            )
        }.filterNotNull()

        return Result.success(similarFaces)
    }
}
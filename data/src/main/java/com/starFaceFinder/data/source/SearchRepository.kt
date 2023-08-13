package com.starFaceFinder.data.source

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.data.source.local.HistoryRepository
import com.starFaceFinder.data.source.network.ISearchDataSource
import java.io.File

class SearchRepository constructor(
    private val searchDataSource: ISearchDataSource,
    private val historyRepository: HistoryRepository
) {
    suspend fun searchSimilarFace(
        fid: Long,
        name: String,
        filename: String,
        image: File
    ) = kotlin.runCatching {
        searchDataSource.searchSimilarFace(name, filename, image)
    }.map { searchedFaces ->
        //비슷한 연예인이 있는 경우 연예인 이미지 검색
        searchedFaces.faces.mapNotNull { face ->
            val celebrityName = face.celebrity?.value ?: return@mapNotNull null
            val confidence = face.celebrity?.confidence ?: return@mapNotNull null

            //연예인 사진 검색
            val searchedImagesResult = searchImage(celebrityName)
            val searchedImages = searchedImagesResult.getOrElse { return@mapNotNull null }
            val firstImage =
                searchedImages.items.firstOrNull { it.sizeheight > it.sizewidth }
                    ?: return@mapNotNull null

            SimilarFace(
                0,
                fid,
                celebrityName,
                confidence,
                firstImage.link,
                firstImage.thumbnail,
                firstImage.sizeheight,
                firstImage.sizewidth
            )
        }
    }.onSuccess { similarFaces ->
        //연예인 사진 검색 결과 저장
        historyRepository.insertSimilarFaceHistory(similarFaces)
    }

    suspend fun searchImage(
        query: String
    ) = kotlin.runCatching {
        searchDataSource.searchImage(query)
    }

    suspend fun searchFaceInfo(
        name: String,
        filename: String,
        image: File
    ): Result<FaceInfo?> = kotlin.runCatching {
        searchDataSource.searchFaceInfo(name, filename, image).toFaceInfo()
    }.onSuccess { faceInfo ->
        //얼굴 인식에 성공할 경우 insert history
        faceInfo?.let {
            faceInfo.fid = historyRepository.insertFaceInfoHistory(faceInfo)
        }
    }
}
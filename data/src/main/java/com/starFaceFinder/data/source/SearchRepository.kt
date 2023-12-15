package com.starFaceFinder.data.source

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.data.source.local.IHistoryLocalDataSource
import com.starFaceFinder.data.source.network.ISearchRemoteDataSource
import java.io.File
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchDataSource: ISearchRemoteDataSource,
    private val historyLocalDataSource: IHistoryLocalDataSource
) {
    suspend fun searchSimilarFace(
        fid: Long,
        name: String,
        filename: String,
        image: File
    ) =
        searchDataSource.searchSimilarFace(name, filename, image)
            .map { searchedFaces ->
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
                historyLocalDataSource.insertSimilarFaceHistory(similarFaces)
            }

    private suspend fun searchImage(
        query: String
    ) = searchDataSource.searchImage(query)


    suspend fun searchFaceInfo(
        name: String,
        filename: String,
        image: File
    ): Result<FaceInfo?> =
        searchDataSource.searchFaceInfo(name, filename, image)
            .map {
                it.toFaceInfo(image)
            }.onSuccess { faceInfo ->
                //얼굴 인식에 성공할 경우 insert history
                faceInfo?.let {
                    faceInfo.fid = historyLocalDataSource.insertFaceInfoHistory(faceInfo)
                }
            }
}
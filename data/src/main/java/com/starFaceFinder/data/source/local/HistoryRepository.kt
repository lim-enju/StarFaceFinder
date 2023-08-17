package com.starFaceFinder.data.source.local

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getAllHistory(limit: Int, offset: Int) =
        database.faceInfoDao().getFaceHistories(limit, offset)

    fun insertFaceInfoHistory(faceInfo: FaceInfo) =
        database.faceInfoDao().insertFaceInfo(faceInfo)


    fun insertSimilarFaceHistory(similarFaces: List<SimilarFace>) {
        database.similarFacesDao().insertSimilarFaceDao(similarFaces)
    }
}
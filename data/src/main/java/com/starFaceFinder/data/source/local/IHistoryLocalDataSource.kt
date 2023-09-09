package com.starFaceFinder.data.source.local

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import kotlinx.coroutines.flow.Flow

interface IHistoryLocalDataSource {
    fun getHistoryDetail(fid: Long): Map<FaceInfo, List<SimilarFace>>

    fun getAllHistory(limit: Int, offset: Int): Flow<Map<FaceInfo, List<SimilarFace>>>

    fun insertFaceInfoHistory(faceInfo: FaceInfo): Long

    fun insertSimilarFaceHistory(similarFaces: List<SimilarFace>)
}
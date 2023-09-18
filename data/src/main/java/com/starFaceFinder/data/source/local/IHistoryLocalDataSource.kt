package com.starFaceFinder.data.source.local

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import kotlinx.coroutines.flow.Flow

interface IHistoryLocalDataSource {
    fun getHistoryDetail(fid: Long): Map<FaceInfo, List<SimilarFace>>

    fun getFaceHistories(limit: Int, offset: Int): Map<FaceInfo, List<SimilarFace>>

    fun getSearchedFaceHistories(text: String, limit: Int, offset: Int): Map<FaceInfo, List<SimilarFace>>

    fun insertFaceInfoHistory(faceInfo: FaceInfo): Long

    fun insertSimilarFaceHistory(similarFaces: List<SimilarFace>)
}
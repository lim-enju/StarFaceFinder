package com.starFaceFinder.data.source.local

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryLocalDataSource @Inject constructor(
    private val database: AppDatabase
) : IHistoryLocalDataSource {
    override fun getHistoryDetail(fid: Long): Map<FaceInfo, List<SimilarFace>> =
        database.faceInfoDao().getFaceHistory(fid)

    override fun getFaceHistories(limit: Int, offset: Int): Map<FaceInfo, List<SimilarFace>> =
        database.faceInfoDao().getFaceHistories(limit, offset)

    override fun insertFaceInfoHistory(faceInfo: FaceInfo): Long =
        database.faceInfoDao().insertFaceInfo(faceInfo)

    override fun insertSimilarFaceHistory(similarFaces: List<SimilarFace>) =
        database.similarFacesDao().insertSimilarFaceDao(similarFaces)
}
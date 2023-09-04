package com.starFaceFinder.data.source.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.data.pagingSource.HistoryPagingSource
import com.starFaceFinder.data.source.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val database: AppDatabase,
) {
    fun getHistoryDetail(fid: Long) =
        database.faceInfoDao().getFaceHistory(fid)

    fun getAllHistory(limit: Int, offset: Int) =
        database.faceInfoDao().getFaceHistories(limit, offset)

    suspend fun insertFaceInfoHistory(faceInfo: FaceInfo) = withContext(Dispatchers.IO) {
        database.faceInfoDao().insertFaceInfo(faceInfo)
    }

    fun insertSimilarFaceHistory(similarFaces: List<SimilarFace>) {
        database.similarFacesDao().insertSimilarFaceDao(similarFaces)
    }
}
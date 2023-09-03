package com.starFaceFinder.data.source.local

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.data.pagingSource.HistoryPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getHistoryDetail(fid: Long) =
        database.faceInfoDao().getFaceHistory(fid)

    suspend fun getAllHistory(limit: Int, offset: Int) = withContext(Dispatchers.IO) {
        database.faceInfoDao().getFaceHistories(limit, offset)
    }

    suspend fun insertFaceInfoHistory(faceInfo: FaceInfo) = withContext(Dispatchers.IO){
        database.faceInfoDao().insertFaceInfo(faceInfo)
    }

    fun insertSimilarFaceHistory(similarFaces: List<SimilarFace>) {
        database.similarFacesDao().insertSimilarFaceDao(similarFaces)
    }

    val histories
        = Pager(
            config = PagingConfig(pageSize = 10)
        ) { HistoryPagingSource(this) }
        .flow
}
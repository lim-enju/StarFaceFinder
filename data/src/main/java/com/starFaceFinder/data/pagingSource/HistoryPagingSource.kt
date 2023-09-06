package com.starFaceFinder.data.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.data.model.FaceInfoHistory
import com.starFaceFinder.data.source.local.HistoryRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HistoryPagingSource @Inject constructor(
    private val historyRepository: HistoryRepository
) : PagingSource<Int, FaceInfoHistory>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FaceInfoHistory> {
        try {
            // Start refresh at page 1 if undefined.
            val page = params.key ?: 1
            val response = historyRepository.getAllHistory(10, page - 1)
            Log.d(TAG, "load: ${response.size}")
            val transformedData = response.map { (faceInfo, list) ->
                FaceInfoHistory(faceInfo, list)
            }

            // 이전 페이지와 다음 페이지를 결정합니다.
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (transformedData.isEmpty()) null else page + 1

            return LoadResult.Page(
                data = transformedData,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FaceInfoHistory>): Int? {
        return null
    }
}
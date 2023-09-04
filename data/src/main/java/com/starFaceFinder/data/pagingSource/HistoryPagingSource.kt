package com.starFaceFinder.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.starFaceFinder.data.model.FaceInfoHistory
import com.starFaceFinder.data.source.UserPreferencesRepository
import com.starFaceFinder.data.source.local.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HistoryPagingSource @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : PagingSource<Int, FaceInfoHistory>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FaceInfoHistory> {
        try {
            // Start refresh at page 1 if undefined.
            val page = params.key ?: 1
            val response = flow {
                val histories = historyRepository.getAllHistory(10, page - 1)
                val prefs = userPreferencesRepository.userPreferencesFlow.first()
                val result = histories.map { (faceInfo, list) ->
                    faceInfo.isFavorite = prefs.favoritesFaceInfo.contains(faceInfo.fid)
                    FaceInfoHistory(faceInfo, list)
                }
                emit(result)
            }.flowOn(Dispatchers.IO)
                .first()


            // 이전 페이지와 다음 페이지를 결정합니다.
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.isEmpty()) null else page + 1

            return LoadResult.Page(
                data = response,
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
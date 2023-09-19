package com.starFaceFinder.domain.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.data.model.FaceInfoHistory
import com.starFaceFinder.data.source.HistoryRepository
import com.starFaceFinder.domain.usecase.GetSearchedHistoryFaceListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchedHistoriesPagingSource @Inject constructor(
    private val getSearchedHistoryFaceListUseCase: GetSearchedHistoryFaceListUseCase,
    private val query: String
) : PagingSource<Int, FaceInfoHistory>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, FaceInfoHistory> {
        return try {
            // Start refresh at page 1 if undefined.
            withContext(Dispatchers.IO){
                val nextPageNumber = params.key ?: 0
                val response = getSearchedHistoryFaceListUseCase.invoke(query, params.loadSize, nextPageNumber)
                Log.d(TAG, "load: $query ${response.size} $nextPageNumber")
                LoadResult.Page(
                    data = response,
                    prevKey = null, // Only paging forward.
                    nextKey = if(response.isEmpty()) null else nextPageNumber + 1
                )
            }
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FaceInfoHistory>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
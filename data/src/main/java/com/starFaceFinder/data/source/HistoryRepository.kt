package com.starFaceFinder.data.source

import com.starFaceFinder.data.source.local.HistoryLocalDataSource
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyLocalDataSource: HistoryLocalDataSource
) {
    fun getHistoryDetail(fid: Long) = historyLocalDataSource.getHistoryDetail(fid)

    fun getFaceHistories(limit: Int, offset: Int) = historyLocalDataSource.getFaceHistories(limit, offset)

    fun getSearchedHistories(text: String, limit: Int, offset: Int) = historyLocalDataSource.getSearchedFaceHistories(text, limit, offset)
}
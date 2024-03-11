package com.starFaceFinder.data.source

import com.starFaceFinder.data.source.local.HistoryLocalDataSource
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyLocalDataSource: HistoryLocalDataSource
): IHistoryRepository {
    override fun getHistoryDetail(fid: Long) = historyLocalDataSource.getHistoryDetail(fid)

    override fun getFaceHistories(limit: Int, offset: Int) = historyLocalDataSource.getFaceHistories(limit, offset)

    override fun getSearchedHistories(text: String, limit: Int, offset: Int) = historyLocalDataSource.getSearchedFaceHistories(text, limit, offset)
}
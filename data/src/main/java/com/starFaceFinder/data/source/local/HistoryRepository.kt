package com.starFaceFinder.data.source.local

import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getAllHistory() =
        database.faceInfoDao().getAllFaceHistory()
}
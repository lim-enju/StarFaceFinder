package com.starFaceFinder.domain.usecase

import android.util.Log
import com.starFaceFinder.data.source.local.HistoryRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import androidx.paging.map
import com.starFaceFinder.data.common.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class GetHistoryFaceListUseCase @Inject constructor(
    historyRepository: HistoryRepository,
    userPreferencesUseCase: GetUserPreferencesUseCase
) {
    val histories =
        historyRepository
            .histories
            .combine(userPreferencesUseCase.pref){ pagerData, pref ->
                pagerData.map { history ->
                    val faceInfo = history.faceInfo
                    faceInfo.isFavorite = pref.favoritesFaceInfo.contains(faceInfo.fid)
                }
                pagerData
            }
}
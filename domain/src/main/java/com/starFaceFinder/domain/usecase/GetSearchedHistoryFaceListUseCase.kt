package com.starFaceFinder.domain.usecase

import android.util.Log
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.data.model.FaceInfoHistory
import com.starFaceFinder.data.source.HistoryRepository
import com.starFaceFinder.data.source.IHistoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSearchedHistoryFaceListUseCase @Inject constructor(
    private val historyRepository: IHistoryRepository,
    private val userPreferencesUseCase: GetUserPreferencesUseCase
) {
    suspend fun invoke(
        searchedText: String,
        limit: Int,
        offset: Int,
    ) =
        historyRepository
            .getSearchedHistories(searchedText, limit, offset)
            .map { (faceInfo, list) ->
                val pref = userPreferencesUseCase.pref.first()
                val isFavorite = pref.favoritesFaceInfo.contains(faceInfo.fid)
                Log.d(TAG, "invoke: ${list.firstOrNull()?.name}")
                FaceInfoHistory(
                    faceInfo = faceInfo,
                    similarFaceList = list,
                    isFavorite = isFavorite
                )
            }
}
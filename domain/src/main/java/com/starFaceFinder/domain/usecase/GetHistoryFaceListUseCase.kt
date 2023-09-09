package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.model.FaceInfoHistory
import com.starFaceFinder.data.source.HistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHistoryFaceListUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val userPreferencesUseCase: GetUserPreferencesUseCase
) {
    suspend fun invoke(
        limit: Int,
        offset: Int,
    ) =
        historyRepository
            .getAllHistory(limit, offset)
            .map { histories ->
                val pref = userPreferencesUseCase.pref.first()
                histories.map { (faceInfo, list) ->
                    val isFavorite = pref.favoritesFaceInfo.contains(faceInfo.fid)
                    FaceInfoHistory(
                        faceInfo = faceInfo,
                        similarFaceList = list,
                        isFavorite = isFavorite
                    )
                }
            }
}
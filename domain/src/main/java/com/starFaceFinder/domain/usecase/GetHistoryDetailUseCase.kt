package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.model.FaceInfoHistory
import com.starFaceFinder.data.source.HistoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetHistoryDetailUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val userPreferencesUseCase: GetUserPreferencesUseCase
) {
    suspend fun invoke(fid: Long) =
        historyRepository.getHistoryDetail(fid)
            .map { (faceInfo, list) ->
                val pref = userPreferencesUseCase.pref.first()
                val isFavorite = pref.favoritesFaceInfo.contains(faceInfo.fid)
                FaceInfoHistory(
                    faceInfo = faceInfo,
                    similarFaceList = list,
                    isFavorite = isFavorite
                )
            }
}
package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.data.source.UserPreferencesRepository
import com.starFaceFinder.data.source.HistoryRepository
import javax.inject.Inject

class UpdateFavoritesFaceInfoUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val historyRepository: HistoryRepository
) {
    suspend fun invoke(fid: Long, isFavorite: Boolean): Map<FaceInfo, List<SimilarFace>> {
        //게시글 favorite 정보 업데이트
        userPreferencesRepository.updateFavoriteFaceInfo(fid, isFavorite)

        //업데이트 된 history 를 반환함
        return historyRepository.getHistoryDetail(fid)
    }
}
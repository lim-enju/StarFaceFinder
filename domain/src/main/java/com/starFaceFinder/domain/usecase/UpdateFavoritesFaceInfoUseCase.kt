package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.source.UserPreferencesRepository
import javax.inject.Inject

class UpdateFavoritesFaceInfoUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun invoke(fid: Long, isFavorite: Boolean) {
        userPreferencesRepository.updateFavoriteFaceInfo(fid, isFavorite)
    }
}
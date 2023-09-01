package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.source.UserPreferencesRepository
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    fun invoke() = userPreferencesRepository.userPreferencesFlow
}
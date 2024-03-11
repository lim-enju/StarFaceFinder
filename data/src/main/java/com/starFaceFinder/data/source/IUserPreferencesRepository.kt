package com.starFaceFinder.data.source

import com.starFaceFinder.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface IUserPreferencesRepository {
    val userPreferencesFlow: Flow<UserData>
    suspend fun updateFavoriteFaceInfo(fid: Long, isFavorite: Boolean)
}
package com.starFaceFinder.data.source

import android.content.Context
import android.util.Log
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.data.datastore.UserPreferences
import com.starFaceFinder.data.model.UserData
import com.starFaceFinder.data.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
   private val context: Context
) {
    val userPreferencesFlow: Flow<UserData> = context.dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }.map {
            UserData(
                it.favoritesFaceInfoList.toSet()
            )
        }

    suspend fun updateFavoriteFaceInfo(fid: Long, isFavorite: Boolean) {
        context.dataStore.updateData { preferences ->
            Log.d(TAG, "repo: $fid $isFavorite")
            if (isFavorite) {
                preferences.toBuilder().addFavoritesFaceInfo(fid).build()
            } else {
                val updatedFavorites = preferences.favoritesFaceInfoList.filter { it != fid }
                preferences.toBuilder()
                    .clearFavoritesFaceInfo()
                    .addAllFavoritesFaceInfo(updatedFavorites)
                    .build()
            }
        }
    }
}
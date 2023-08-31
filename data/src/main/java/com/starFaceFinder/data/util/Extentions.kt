package com.starFaceFinder.data.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import com.starFaceFinder.data.datastore.UserPreferences
import com.starFaceFinder.data.serializer.UserPreferencesSerializer

private const val USER_PREFERENCES_NAME = "user_preferences"
private const val DATA_STORE_FILE_NAME = "user_prefs.pb"
private const val SORT_ORDER_KEY = "sort_order"

val Context.dataStore: DataStore<UserPreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserPreferencesSerializer
)
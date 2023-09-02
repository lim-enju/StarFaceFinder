package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.domain.usecase.GetHistoryFaceListUseCase
import com.starFaceFinder.domain.usecase.GetUserPreferencesUseCase
import com.starFaceFinder.domain.usecase.UpdateFavoritesFaceInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getHistoryFaceListUseCase: GetHistoryFaceListUseCase,
    private val updateFavoritesFaceInfoUseCase: UpdateFavoritesFaceInfoUseCase
) : ViewModel() {

    var offset = 0
    var limit = 10

    val histories = getHistoryFaceListUseCase.invoke(limit, offset)
        .onEach { histories ->
            if (histories.isNotEmpty()) offset += histories.size
        }

    fun updateFavoriteFaceInfo(fid: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "updateFavoriteFaceInfo: $fid $isFavorite")
            updateFavoritesFaceInfoUseCase.invoke(fid, isFavorite)
        }
    }
}
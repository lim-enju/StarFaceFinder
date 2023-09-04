package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.starFaceFinder.domain.usecase.GetHistoryFaceListUseCase
import com.starFaceFinder.domain.usecase.GetUserPreferencesUseCase
import com.starFaceFinder.domain.usecase.UpdateFavoritesFaceInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getHistoryFaceListUseCase: GetHistoryFaceListUseCase,
    private val updateFavoritesFaceInfoUseCase: UpdateFavoritesFaceInfoUseCase,
) : ViewModel() {

    val histories =
        getHistoryFaceListUseCase
            .histories
            .cachedIn(viewModelScope)

    fun updateFavoriteFaceInfo(fid: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateFavoritesFaceInfoUseCase.invoke(fid, isFavorite)
        }
    }
}
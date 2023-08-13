package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starFaceFinder.domain.usecase.GetHistoryFaceListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getHistoryFaceListUseCase: GetHistoryFaceListUseCase
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val map = getHistoryFaceListUseCase.invoke()
            map.entries
        }
    }
}
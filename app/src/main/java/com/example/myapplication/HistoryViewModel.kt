package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.domain.usecase.GetHistoryFaceListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryFaceListUseCase: GetHistoryFaceListUseCase
) : ViewModel() {
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val map = getHistoryFaceListUseCase.invoke()
            Log.d(TAG, ": ${map.entries}")
        }
    }
}
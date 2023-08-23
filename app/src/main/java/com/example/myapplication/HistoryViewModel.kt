package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.domain.usecase.GetHistoryFaceListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryFaceListUseCase: GetHistoryFaceListUseCase
) : ViewModel() {

    var offset = 0
    var limit = 10

    fun getHistories() =
        getHistoryFaceListUseCase.invoke(limit, offset)
            .onEach { histories ->
                if(histories.isNotEmpty()) offset ++
            }
}
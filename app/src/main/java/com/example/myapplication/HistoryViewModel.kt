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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryFaceListUseCase: GetHistoryFaceListUseCase
) : ViewModel() {

    var offset = 0
    var limit = 10

    private val _histories: MutableStateFlow<ArrayList<Pair<FaceInfo, List<SimilarFace>>>> = MutableStateFlow(
        arrayListOf()
    )
    val histories = _histories.asStateFlow()

    init {
        getHistories()
    }

    fun getHistories() {
        viewModelScope.launch(Dispatchers.IO){
            val historyMap = getHistoryFaceListUseCase.invoke(limit, offset)
            if(historyMap.isNotEmpty()){
                offset++

                val historyList = _histories.value + historyMap.map { entry ->
                    Pair(entry.key, entry.value)
                }
                _histories.emit(ArrayList(historyList))
            }
        }
    }
}
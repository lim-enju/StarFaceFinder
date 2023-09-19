package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myapplication.model.HistoryUiState
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.domain.pagingSource.SearchedHistoriesPagingSource
import com.starFaceFinder.domain.usecase.GetHistoryFaceListUseCase
import com.starFaceFinder.domain.usecase.GetSearchedHistoryFaceListUseCase
import com.starFaceFinder.domain.usecase.UpdateFavoritesFaceInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryFaceListUseCase: GetHistoryFaceListUseCase,
    private val updateFavoritesFaceInfoUseCase: UpdateFavoritesFaceInfoUseCase,
    private val getSearchedHistoryFaceListUseCase: GetSearchedHistoryFaceListUseCase
) : ViewModel() {

    private val _historyUiState = MutableStateFlow(HistoryUiState())
    val historyUiState = _historyUiState.asStateFlow()

    private val _updatedHistoryItem = MutableSharedFlow<Int>()
    val updatedHistoryItem: SharedFlow<Int> = _updatedHistoryItem.asSharedFlow()

    private val _toastMsg = MutableSharedFlow<String>()
    val toastMsg: SharedFlow<String> = _toastMsg.asSharedFlow()

    val searchedText = MutableStateFlow<String?>(null)

    private var offset = 0
    private var limit = 10


    private fun searchHistories(searchedText: String) =
        Pager(
            PagingConfig(pageSize = limit)
        ) {
            SearchedHistoriesPagingSource(getSearchedHistoryFaceListUseCase, searchedText)
        }.flow
            .flowOn(Dispatchers.IO)

    val searchedHistoriesFlow = searchedText
        .flatMapLatest {  searchedText ->
            searchHistories(searchedText?:"")
        }.cachedIn(viewModelScope)

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val histories = _historyUiState.value.historyItems
            val loadedHistories = getHistoryFaceListUseCase.invoke(limit, offset)

            _historyUiState.update {
                it.copy(
                    historyItems = ArrayList(histories + loadedHistories)
                )
            }

            //다음 페이지 조회를 위해 offset + 1
            if (loadedHistories.isNotEmpty()) offset++

            //히스토리가 없는 경우
            if (_historyUiState.value.historyItems.isEmpty()) {
                _toastMsg.emit("홈>닮은 연예인 찾기 에서 닮은 연예인을 찾아보세요")
            }
        }
    }

    fun updateFavorite(fid: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateFavoritesFaceInfoUseCase.invoke(fid, isFavorite)

            //업데이트 된 게시글을 찾아 리스트에 업데이트
            val historyUiState = _historyUiState.value
            val updatedHistory =
                historyUiState.historyItems.firstOrNull { it.faceInfo.fid == fid } ?: return@launch
            val updatedHistoryIndex = historyUiState.historyItems.indexOf(updatedHistory)

            historyUiState.historyItems[updatedHistoryIndex] = updatedHistory.copy(
                isFavorite = isFavorite
            )

            //게시글 한개가 업데이트 되었음을 알림
            _updatedHistoryItem.emit(updatedHistoryIndex)
        }
    }
}
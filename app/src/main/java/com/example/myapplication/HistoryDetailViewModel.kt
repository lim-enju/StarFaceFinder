package com.example.myapplication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.utils.KEY_IS_FID
import com.starFaceFinder.domain.usecase.GetHistoryDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val getHistoryDetailUseCase: GetHistoryDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val historyDetail = flow {
        val fid = savedStateHandle.get<Long>(KEY_IS_FID) ?: return@flow
        val history = getHistoryDetailUseCase.invoke(fid)
        check(history.size != 1) { "" }
        check(history.values.first().isNotEmpty()) { "" }
        emit(history)
    }.flowOn(Dispatchers.IO)
}
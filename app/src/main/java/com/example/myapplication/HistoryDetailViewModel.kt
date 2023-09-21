package com.example.myapplication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.utils.KEY_IS_FID
import com.starFaceFinder.data.model.FaceInfoHistory
import com.starFaceFinder.domain.usecase.GetHistoryDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val getHistoryDetailUseCase: GetHistoryDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val historyDetail: Flow<FaceInfoHistory> =
        savedStateHandle.getStateFlow<Long>(KEY_IS_FID, 0)
            .filter { fid -> fid.toInt() != 0 }
            .map { fid ->
                val history = getHistoryDetailUseCase.invoke(fid)
                check(history.size == 1) { "" }
                history.first()
            }.flowOn(Dispatchers.IO)
}
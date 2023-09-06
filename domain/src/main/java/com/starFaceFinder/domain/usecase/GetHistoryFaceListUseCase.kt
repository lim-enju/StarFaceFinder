package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.source.local.HistoryRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetHistoryFaceListUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
) {
    fun invoke(limit: Int, offset: Int) =
        historyRepository
            .getAllHistory(limit, offset)
}
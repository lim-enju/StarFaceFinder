package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.source.local.HistoryRepository
import javax.inject.Inject

class GetHistoryFaceListUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    fun invoke() = historyRepository.getAllHistory()
}
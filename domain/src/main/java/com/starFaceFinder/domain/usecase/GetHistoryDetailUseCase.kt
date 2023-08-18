package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.source.local.HistoryRepository
import javax.inject.Inject

class GetHistoryDetailUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    fun invoke(fid: Long) = historyRepository.getHistoryDetail(fid)
}
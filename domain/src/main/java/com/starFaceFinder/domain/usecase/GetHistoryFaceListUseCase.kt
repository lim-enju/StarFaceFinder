package com.starFaceFinder.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.starFaceFinder.data.pagingSource.HistoryPagingSource
import com.starFaceFinder.data.source.UserPreferencesRepository
import com.starFaceFinder.data.source.local.HistoryRepository
import javax.inject.Inject

class GetHistoryFaceListUseCase @Inject constructor(
    historyRepository: HistoryRepository,
    userPreferencesRepository: UserPreferencesRepository
) {

    val histories = Pager(
        config = PagingConfig(pageSize = 10)
    ) {
        HistoryPagingSource(historyRepository, userPreferencesRepository)
    }.flow
}
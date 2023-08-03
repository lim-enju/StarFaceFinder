package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.source.SearchRepository
import javax.inject.Inject

class GetHistoryFaceListUseCase @Inject constructor(
    val searchRepository: SearchRepository
){

}
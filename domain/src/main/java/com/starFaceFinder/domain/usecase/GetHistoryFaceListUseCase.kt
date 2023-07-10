package com.starFaceFinder.domain.usecase

import com.starFaceFinder.data.source.FindFaceRepository
import javax.inject.Inject

class GetHistoryFaceListUseCase @Inject constructor(
    val findFaceRepository: FindFaceRepository
){

}
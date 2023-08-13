package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.example.myapplication.delegate.FileDelegation
import com.example.myapplication.delegate.IFileDelegation
import com.example.myapplication.utils.KEY_IS_SELECTED_URI
import com.example.myapplication.utils.context
import com.starFaceFinder.domain.usecase.SearchFaceInfoUseCase
import com.starFaceFinder.domain.usecase.SearchSimilarFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.FileNotFoundException
import javax.inject.Inject

@HiltViewModel
class FindFaceViewModel @Inject constructor(
    application: Application,
    private val searchSimilarFaceUseCase: SearchSimilarFaceUseCase,
    private val searchFaceInfoUseCase: SearchFaceInfoUseCase,
    private val savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    private val fileDelegation: IFileDelegation by lazy { FileDelegation(context) }

    val imageFile = flow {
        val imageUri =
            savedStateHandle.get<String>(KEY_IS_SELECTED_URI) ?: throw FileNotFoundException()
        val file = fileDelegation.uriToFile(imageUri) ?: throw FileNotFoundException()
        emit(file)
    }

    val searchedFaceInfo = imageFile
        .map { file ->
            searchFaceInfoUseCase.invoke(file)
        }.flowOn(Dispatchers.IO)

    //TODO:: resize 버그 수정하기
    //        fileDelegation.resizeImage(file)?.let { resized ->
//            searchSimilarFaceUseCase.invoke(resized)
//        }
    //얼굴 정보 검색이 완료된 경우 유사한 연예인을 검색함
    val searchedSimilarFace =
        imageFile.combine(searchedFaceInfo) { file, faceInfoResult ->
            val fid = faceInfoResult.getOrNull()?.fid ?: return@combine Result.failure(Throwable())
            searchSimilarFaceUseCase.invoke(fid, file)
        }.flowOn(Dispatchers.IO)
}
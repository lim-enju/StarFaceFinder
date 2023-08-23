package com.example.myapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myapplication.delegate.FileDelegation
import com.example.myapplication.delegate.IFileDelegation
import com.example.myapplication.utils.KEY_IS_SELECTED_URI
import com.example.myapplication.utils.context
import com.example.myapplication.utils.getRandomString
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.domain.usecase.SearchFaceInfoUseCase
import com.starFaceFinder.domain.usecase.SearchSimilarFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import java.io.File
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

    // file을 캐싱하기 위해 shareIn을 사용함
    // WhileSubscribed 는 이미 계산된 결과를 재사용
    val imageFile = flow {
        val imageUri = savedStateHandle.get<String>(KEY_IS_SELECTED_URI)
        check(imageUri != null)

        val bitmap = fileDelegation.uriToBitmap(imageUri)
        check(bitmap != null)

        val resizedBitmap = fileDelegation.compressImage(bitmap, 2 * 1024 * 1024)
        check(resizedBitmap != null)

        val file = fileDelegation.saveFile("${getRandomString(20)}.jpg", resizedBitmap)
        emit(file)
    }.flowOn(Dispatchers.IO)

    val searchedFaceInfo = imageFile
        .map { file ->
            val result = searchFaceInfoUseCase.invoke(file)
            check(result.isSuccess) { result.exceptionOrNull()?.message ?: "" }
            result
        }.flowOn(Dispatchers.IO)


    //얼굴 정보 검색이 완료된 경우 유사한 연예인을 검색함
    val searchedSimilarFace =
        imageFile.combine(searchedFaceInfo) { file, faceInfoResult ->
            val fid = faceInfoResult.getOrNull()?.fid
            check(fid != null) { throw Throwable("fid 값이 올바르지 않습니다.") }
            searchSimilarFaceUseCase.invoke(fid, file)
        }.flowOn(Dispatchers.IO)
}
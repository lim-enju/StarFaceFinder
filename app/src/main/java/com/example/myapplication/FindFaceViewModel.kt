package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myapplication.delegate.FileDelegation
import com.example.myapplication.delegate.IFileDelegation
import com.example.myapplication.utils.KEY_IS_SELECTED_URI
import com.example.myapplication.utils.context
import com.starFaceFinder.domain.usecase.SearchFaceInfoUseCase
import com.starFaceFinder.domain.usecase.SearchSimilarFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    //TODO:: resize 버그 수정하기
    val searchedSimilarFace =
        imageFile
            .map { file ->
//        fileDelegation.resizeImage(file)?.let { resized ->
//            searchSimilarFaceUseCase.invoke(resized)
//        }
                searchSimilarFaceUseCase.invoke(file)
            }

    val searchedFaceInfo = imageFile
        .map { file ->
            searchFaceInfoUseCase.invoke(file)
        }
}
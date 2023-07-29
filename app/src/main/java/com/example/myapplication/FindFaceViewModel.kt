package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myapplication.delegate.FileInputDelegation
import com.example.myapplication.delegate.IFileInputDelegation
import com.example.myapplication.utils.KEY_IS_SELECTED_URI
import com.example.myapplication.utils.context
import com.starFaceFinder.domain.usecase.FindFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.io.FileNotFoundException
import javax.inject.Inject

@HiltViewModel
class FindFaceViewModel @Inject constructor(
    application: Application,
    private val findFaceUseCase: FindFaceUseCase,
    private val savedStateHandle: SavedStateHandle,
): AndroidViewModel(application) {

    private val fileInputDelegation: IFileInputDelegation by lazy { FileInputDelegation(context) }

    val imageFile = flow {
        val imageUri = savedStateHandle.get<String>(KEY_IS_SELECTED_URI)?: throw FileNotFoundException()
        val file = fileInputDelegation.uriToFile(imageUri)?: throw FileNotFoundException()
        emit(file)
    }

    val findFaceResult = imageFile.map { file ->
        findFaceUseCase.invoke(file)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
}
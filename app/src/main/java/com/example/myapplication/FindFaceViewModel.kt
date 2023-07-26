package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.delegate.FileInputDelegation
import com.example.myapplication.delegate.IFileInputDelegation
import com.example.myapplication.utils.KEY_IS_SELECTED_URI
import com.example.myapplication.utils.context
import com.starFaceFinder.data.model.Face
import com.starFaceFinder.domain.usecase.FindFaceUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindFaceViewModel @Inject constructor(
    application: Application,
    private val findFaceUseCase: FindFaceUseCase,
    private val savedStateHandle: SavedStateHandle,
): AndroidViewModel(application) {

    private val fileInputDelegation: IFileInputDelegation by lazy { FileInputDelegation(context) }

    private val _findFaceResult: MutableSharedFlow<Result<Face>> = MutableSharedFlow()
    val findFaceResult = _findFaceResult.asSharedFlow()

    init {
        fetchFindFace()
    }

    private fun fetchFindFace(){
        val uri = savedStateHandle.get<String>(KEY_IS_SELECTED_URI)?: return
        val file = fileInputDelegation.uriToFile(uri)?: return

        viewModelScope.launch(Dispatchers.IO){
            val result = findFaceUseCase.invoke(file)
            _findFaceResult.emit(result)
        }
    }
}
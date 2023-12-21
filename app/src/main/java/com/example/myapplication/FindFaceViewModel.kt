package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myapplication.delegate.FileDelegation
import com.example.myapplication.delegate.IFileDelegation
import com.example.myapplication.utils.KEY_IS_SELECTED_URI
import com.example.myapplication.utils.context
import com.example.myapplication.utils.getRandomString
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.domain.usecase.SearchFaceInfoUseCase
import com.starFaceFinder.domain.usecase.SearchSimilarFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FindFaceViewModel @Inject constructor(
    application: Application,
    private val searchSimilarFaceUseCase: SearchSimilarFaceUseCase,
    private val searchFaceInfoUseCase: SearchFaceInfoUseCase,
    private val savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    private val fileDelegation: IFileDelegation by lazy { FileDelegation(context) }

    private val _toastMsg = MutableSharedFlow<String>()
    val toastMsg = _toastMsg.asSharedFlow()

    private val _faceImgFile = MutableStateFlow<File?>(null)
    val faceImgFile = _faceImgFile.asStateFlow()

    private val _searchFaceInfo = MutableStateFlow<FaceInfo?>(null)
    val searchFaceInfo = _searchFaceInfo.asStateFlow()

    private val _similarFaceInfos = MutableStateFlow<List<SimilarFace>>(listOf())
    val similarFaceInfos = _similarFaceInfos.asStateFlow()

    init {
        searchFaceInfo()
    }

    private fun searchFaceInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            //uri를 이미지로 변환
            val file = getImageFile()
            if(file == null){
                _toastMsg.emit("파일을 찾지 못했습니다.")
                return@launch
            }
            _faceImgFile.emit(file)

            //얼굴 정보 조회
            val result = searchFaceInfoUseCase.invoke(file)
            val msg = when{
                result.isFailure -> result.exceptionOrNull()?.message ?: ""
                result.getOrNull()?.fid == null -> "닮은 연예인을 찾지 못했습니다."
                else -> null
            }

            if(msg != null){
                _toastMsg.emit(msg)
                return@launch
            }

            _searchFaceInfo.emit(result.getOrNull())

            //유사한 얼굴 정보 조회
            val fid = result.getOrNull()?.fid
            if(fid == null) {
                _toastMsg.emit("fid 값이 올바르지 않습니다.")
                return@launch
            }

            val similarFaces = searchSimilarFaceUseCase.invoke(fid, file).getOrNull()?: listOf()
            _similarFaceInfos.emit(similarFaces)
        }
    }

    private fun getImageFile():File?{
        val imageUri = savedStateHandle.get<String>(KEY_IS_SELECTED_URI)?: return null

        val bitmap = fileDelegation.uriToBitmap(imageUri)
        check(bitmap != null)

        val resizedBitmap = fileDelegation.compressImage(bitmap, 500 * 1024)
        check(resizedBitmap != null)

        return fileDelegation.saveFile("${getRandomString(20)}.jpg", resizedBitmap)
    }
}
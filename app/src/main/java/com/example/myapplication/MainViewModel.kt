package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.domain.usecase.FindFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val findFaceUseCase: FindFaceUseCase
): ViewModel() {
    fun findFace(file: File){
        viewModelScope.launch (Dispatchers.IO){
            findFaceUseCase.invoke(file)
                .collect{
                    Log.d(TAG, "findFace: $it")
                }
        }
    }
}
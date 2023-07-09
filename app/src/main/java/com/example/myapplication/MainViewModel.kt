package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starFaceFinder.data.model.request.FindFaceRequest
import com.starFaceFinder.data.source.FindFaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repo: FindFaceRepository
): ViewModel() {

    fun init(){
        viewModelScope.launch (Dispatchers.IO){
            repo.getFindFaceResult(FindFaceRequest("", "", File("")))
        }
    }
}
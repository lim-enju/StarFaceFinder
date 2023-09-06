package com.example.myapplication.model

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace

data class HistoryUiState (
    val historyItems: ArrayList<HistoryItemUiState> = arrayListOf()
)

data class HistoryItemUiState(
    val faceInfo: FaceInfo,
    val similarFaceList: List<SimilarFace>,
    val isFavorite: Boolean,
    val onFavorite: () -> Unit
)
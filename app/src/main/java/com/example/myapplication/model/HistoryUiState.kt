package com.example.myapplication.model

import com.starFaceFinder.data.model.FaceInfoHistory

data class HistoryUiState(
    val historyItems: ArrayList<FaceInfoHistory> = arrayListOf(),
    val searchedHistoryItems: ArrayList<FaceInfoHistory> = arrayListOf()
)
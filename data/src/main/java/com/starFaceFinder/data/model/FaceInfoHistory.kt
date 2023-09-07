package com.starFaceFinder.data.model

data class FaceInfoHistory(
    val faceInfo: FaceInfo,
    val similarFaceList: List<SimilarFace>,
    val isFavorite: Boolean,
)
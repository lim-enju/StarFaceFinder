package com.starFaceFinder.data.model

data class FaceInfoHistory (
    val faceInfo: FaceInfo,
    val similarFaces: List<SimilarFace>
)
package com.starFaceFinder.data.model.request

import java.io.File

data class FindFaceRequest (
    val name: String,
    val filename: String,
    val image: File
)
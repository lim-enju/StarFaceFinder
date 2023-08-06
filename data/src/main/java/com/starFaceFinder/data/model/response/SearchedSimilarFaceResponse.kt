package com.starFaceFinder.data.model.response

import com.google.gson.annotations.SerializedName

data class SearchedSimilarFaceResponse(
    @SerializedName("info")
    var info: ImageInfo? = null,

    @SerializedName("faces")
    var faces: ArrayList<Face> = arrayListOf()
)


data class Celebrity(
    @SerializedName("value")
    var value: String? = null,

    @SerializedName("confidence")
    var confidence: Double? = null
)

data class Face(
    @SerializedName("celebrity")
    var celebrity: Celebrity? = Celebrity()
)
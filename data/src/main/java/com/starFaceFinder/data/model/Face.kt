package com.starFaceFinder.data.model

import com.google.gson.annotations.SerializedName

data class Face(
    @SerializedName("info")
    var info: Info? = null,

    @SerializedName("faces")
    var faces: ArrayList<Faces> = arrayListOf()
)

data class Size(
    @SerializedName("width")
    var width: Int? = null,

    @SerializedName("height")
    var height: Int? = null
)

data class Info(
    @SerializedName("size")
    var size: Size? = Size(),

    @SerializedName("faceCount")
    var faceCount: Int? = null
)

data class Celebrity(
    @SerializedName("value")
    var value: String? = null,

    @SerializedName("confidence")
    var confidence: Double? = null
)

data class Faces(
    @SerializedName("celebrity")
    var celebrity: Celebrity? = Celebrity()
)
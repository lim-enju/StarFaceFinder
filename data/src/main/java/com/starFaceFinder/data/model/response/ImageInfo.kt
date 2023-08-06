package com.starFaceFinder.data.model.response

import com.google.gson.annotations.SerializedName

data class Size(
    @SerializedName("width")
    var width: Int? = null,

    @SerializedName("height")
    var height: Int? = null
)

data class ImageInfo(
    @SerializedName("size")
    var size: Size? = Size(),

    @SerializedName("faceCount")
    var faceCount: Int? = null
)
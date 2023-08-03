package com.starFaceFinder.data.model

import com.google.gson.annotations.SerializedName

data class SimilarFaces(
    @SerializedName("value")
    var value: String? = null,

    @SerializedName("confidence")
    var confidence: Double? = null,

    @SerializedName("link")
    var link: String? = null,

    @SerializedName("thumbnail")
    var thumbnail: String? = null,

    @SerializedName("sizeheight")
    var sizeheight: String? = null,

    @SerializedName("sizewidth")
    var sizewidth: String? = null
)
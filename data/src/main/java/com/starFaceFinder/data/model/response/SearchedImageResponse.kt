package com.starFaceFinder.data.model.response

import com.google.gson.annotations.SerializedName

data class SearchedImageResponse(
    @SerializedName("lastBuildDate")
    var lastBuildDate: String? = null,
    @SerializedName("total")
    var total: Int? = null,
    @SerializedName("start")
    var start: Int? = null,
    @SerializedName("display")
    var display: Int? = null,
    @SerializedName("items")
    var items: ArrayList<SearchedImage> = arrayListOf()

)

data class SearchedImage(
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("link")
    var link: String? = null,

    @SerializedName("thumbnail")
    var thumbnail: String? = null,

    @SerializedName("sizeheight")
    var sizeheight: String? = null,

    @SerializedName("sizewidth")
    var sizewidth: String? = null
)
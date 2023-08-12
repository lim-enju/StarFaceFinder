package com.starFaceFinder.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class SimilarFaces(
    @SerializedName("value")
    @ColumnInfo(name = "value")
    var value: String? = null,

    @SerializedName("confidence")
    @ColumnInfo(name = "confidence")
    var confidence: Double? = null,

    @SerializedName("link")
    @ColumnInfo(name = "link")
    var link: String? = null,

    @SerializedName("thumbnail")
    @ColumnInfo(name = "thumbnail")
    var thumbnail: String? = null,

    @SerializedName("sizeheight")
    @ColumnInfo(name = "sizeheight")
    var sizeheight: Float,

    @SerializedName("sizewidth")
    @ColumnInfo(name = "sizewidth")
    var sizewidth: Float
)
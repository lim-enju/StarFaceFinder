package com.starFaceFinder.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SimilarFaces(
    @PrimaryKey val id: Int,

    @SerializedName("value")
    @ColumnInfo(name = "value")
    val value: String? = null,

    @SerializedName("confidence")
    @ColumnInfo(name = "confidence")
    val similarConfidence: Double? = null,

    @SerializedName("link")
    @ColumnInfo(name = "link")
    val link: String? = null,

    @SerializedName("thumbnail")
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String? = null,

    @SerializedName("sizeheight")
    @ColumnInfo(name = "size_height")
    val sizeHeight: Float,

    @SerializedName("sizeidth")
    @ColumnInfo(name = "size_width")
    val sizeWidth: Float
)
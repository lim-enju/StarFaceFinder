package com.starFaceFinder.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = FaceInfo::class,
        parentColumns = arrayOf("fid"),
        childColumns = arrayOf("fid"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class SimilarFace(
    @PrimaryKey(autoGenerate = true) var id: Long,

    @ColumnInfo("fid")
    var fid: Long,

    @SerializedName("value")
    @ColumnInfo(name = "name")
    val name: String? = null,

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
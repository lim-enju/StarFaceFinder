package com.starFaceFinder.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FaceInfo(
    @PrimaryKey(autoGenerate = true) var fid: Long,
    @ColumnInfo("fileUri") val fileUri: String? = null,
    @ColumnInfo("gender") val gender: String? = null,
    @ColumnInfo("gender_confidence") val genderConfidence: Int? = null,
    @ColumnInfo("age") val age: String? = null,
    @ColumnInfo("age_confidence") val ageConfidence: Int? = null,
    var isFavorite: Boolean = false
)

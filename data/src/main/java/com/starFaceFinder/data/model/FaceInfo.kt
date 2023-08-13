package com.starFaceFinder.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.starFaceFinder.data.model.Position

@Entity
data class FaceInfo(
    @PrimaryKey(autoGenerate = true) var fid: Long,
    @ColumnInfo("gender") val gender: String? = null,
    @ColumnInfo("gender_confidence") val genderConfidence: Int? = null,
    @ColumnInfo("age") val age: String? = null,
    @ColumnInfo("age_confidence") val ageConfidence: Int? = null
)

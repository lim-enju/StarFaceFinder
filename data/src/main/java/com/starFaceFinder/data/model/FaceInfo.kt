package com.starFaceFinder.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.starFaceFinder.data.model.Position

@Entity
data class FaceInfo(
    @ColumnInfo("gender") var gender: String? = null,
    @ColumnInfo("genderConfidence") var genderConfidence: Int? = null,
    @ColumnInfo("age") var age: String? = null,
    @ColumnInfo("ageConfidence") var ageConfidence: Int? = null
)

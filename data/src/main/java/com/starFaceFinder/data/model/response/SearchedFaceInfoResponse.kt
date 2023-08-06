package com.starFaceFinder.data.model.response

import com.google.gson.annotations.SerializedName

data class SearchedFaceInfoResponse(
    @SerializedName("info") var info: ImageInfo? = ImageInfo(),
    @SerializedName("faces") var faces: ArrayList<Faces> = arrayListOf()
)

data class Roi(
    @SerializedName("x") var x: Int? = null,
    @SerializedName("y") var y: Int? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null
)

data class Gender(
    @SerializedName("value") var value: String? = null,
    @SerializedName("confidence") var confidence: Double? = null
)

data class Age(
    @SerializedName("value") var value: String? = null,
    @SerializedName("confidence") var confidence: Double? = null
)

data class Emotion(
    @SerializedName("value") var value: String? = null,
    @SerializedName("confidence") var confidence: Double? = null
)

data class Pose(
    @SerializedName("value") var value: String? = null,
    @SerializedName("confidence") var confidence: Double? = null
)

data class Faces(
    @SerializedName("roi") var roi: Roi? = Roi(),
    @SerializedName("landmark") var landmark: String? = null,
    @SerializedName("gender") var gender: Gender? = Gender(),
    @SerializedName("age") var age: Age? = Age(),
    @SerializedName("emotion") var emotion: Emotion? = Emotion(),
    @SerializedName("pose") var pose: Pose? = Pose()
)
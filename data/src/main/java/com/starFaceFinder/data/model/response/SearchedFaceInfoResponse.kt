package com.starFaceFinder.data.model.response

import com.google.gson.annotations.SerializedName
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.Position

data class SearchedFaceInfoResponse(
    @SerializedName("info") var info: ImageInfo? = ImageInfo(),
    @SerializedName("faces") var faces: ArrayList<Faces> = arrayListOf()
){
    fun toFaceInfo(): FaceInfo? {
        val face = faces.firstOrNull()?: return null
        return FaceInfo(
            0,
            face.gender?.value,
            face.gender?.confidence?.toFloat()?.times(100)?.toInt(),
            face.age?.value,
            face.age?.confidence?.toFloat()?.times(100)?.toInt()
        )
    }
}

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

data class Landmark(
    @SerializedName("leftEye") var leftEye: Position? = null,
    @SerializedName("rightEye") var rightEye: Position? = null,
    @SerializedName("nose") var nose: Position? = null,
    @SerializedName("leftMouth") var leftMouth: Position? = null,
    @SerializedName("rightMouth") var rightMouth: Position? = null
)

data class Faces(
    @SerializedName("roi") var roi: Roi? = Roi(),
    @SerializedName("landmark") var landmark: Landmark? = null,
    @SerializedName("gender") var gender: Gender? = Gender(),
    @SerializedName("age") var age: Age? = Age(),
    @SerializedName("emotion") var emotion: Emotion? = Emotion(),
    @SerializedName("pose") var pose: Pose? = Pose()
)
package com.starFaceFinder.data.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Locale

fun Any.getBody(key: String): MultipartBody.Part?
    = when{
        //이미지 파일인 경우
        this is File && isImageFile() -> {
            MultipartBody.Part.createFormData(
                name = key,
                filename = name,
                body = asRequestBody("image/*".toMediaType())
            )
        }
        //이미지 외에 다른 파일인 경우
        this is File -> null
        else -> MultipartBody.Part.createFormData(key, toString())
    }

private fun File.isImageFile(): Boolean{
    val extension = path.substringAfterLast(".", "")
    return when (extension.lowercase(Locale.ROOT)) {
        "jpg", "jpeg", "png", "bmp" -> true
        else -> false
    }
}



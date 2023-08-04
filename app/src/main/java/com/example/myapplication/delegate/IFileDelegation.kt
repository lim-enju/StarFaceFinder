package com.example.myapplication.delegate

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File

interface IFileDelegation {
    //bitmap을 temp파일로 임시저장
    fun saveTempFile(bitmap: Bitmap): File
    fun uriToFile(uri: String): File?

    //file resize
    fun resizeImage(file: File, scaleTo: Int = 1024): File?
}
package com.example.myapplication.delegate

import android.graphics.Bitmap
import java.io.File

interface IFileDelegation {
    //bitmap을 temp파일로 임시저장
    fun saveTempFile(bitmap: Bitmap): File
    fun uriToBitmap(uriString: String): Bitmap?
    fun compressImage(bitmap: Bitmap, maxSizeBytes: Long): Bitmap?
}
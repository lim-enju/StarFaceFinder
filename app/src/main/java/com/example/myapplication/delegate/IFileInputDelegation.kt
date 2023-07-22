package com.example.myapplication.delegate

import android.graphics.Bitmap
import java.io.File

interface IFileInputDelegation {
    fun saveTempFile(bitmap: Bitmap): File

    fun uriToFile(uri: String): File?
}
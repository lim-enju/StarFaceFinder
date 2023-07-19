package com.example.myapplication.delegate

import android.content.Context
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class FileInputDelegation(
    private val context: Context
): IFileInputDelegation {
    override fun saveTempFile(bitmap: Bitmap): File {
        val cacheDir = context.cacheDir
        val file = File.createTempFile("prefix", ".jpg", cacheDir)
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        return file
    }
}
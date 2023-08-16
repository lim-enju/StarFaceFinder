package com.example.myapplication.delegate

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.transition.Transition
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


//TODO:: data 패키지로 이동할 수 있을듯
class FileDelegation(
    private val context: Context
): IFileDelegation {
    override fun saveTempFile(bitmap: Bitmap): File {
        val cacheDir = context.cacheDir
        val file = File.createTempFile("prefix", ".jpg", cacheDir)
        val bytes = ByteArrayOutputStream()

        // Bitmap 복제 및 압축
        val cloneBitmap = bitmap.copy(bitmap.config, true)
        cloneBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        // recycle cloneBitmap
        cloneBitmap.recycle()

        return file
    }

    override fun uriToFile(uri: String): File? = File(uri).takeIf { it.exists() }

    override fun resizeImage(file: File, scaleTo: Int): File {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / scaleTo, photoH / scaleTo)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        val resized = BitmapFactory.decodeFile(file.absolutePath, bmOptions)

        context.openFileOutput(file.name, Context.MODE_PRIVATE).use {
            resized.compress(Bitmap.CompressFormat.JPEG, 75, it)
        }
        return saveTempFile(resized)
    }
}
package com.example.myapplication.delegate

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


//TODO:: data 패키지로 이동할 수 있을듯
class FileDelegation(
    private val context: Context
) : IFileDelegation {
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


    // Uri를 Bitmap으로 변환하는 함수
    override fun uriToBitmap(uriString: String): Bitmap? {
        val contentResolver: ContentResolver = context.contentResolver
        val file = File(uriString)
        val uri = Uri.fromFile(file)
        return try {
            if (Build.VERSION.SDK_INT < 28) {
                // Android 9.0 (Pie) 이하 버전
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } else {
                // Android 9.0 (Pie) 이상 버전
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    //이미지 압축
    override fun compressImage(bitmap: Bitmap, maxSizeBytes: Long): Bitmap? {
        val outputStream = ByteArrayOutputStream()
        var quality = 100 // 초기 품질 설정
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        while (outputStream.toByteArray().size > maxSizeBytes && quality > 0) {
            outputStream.reset() // 출력 스트림 초기화
            quality -= 10 // 품질 조절
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }

        return BitmapFactory.decodeByteArray(
            outputStream.toByteArray(),
            0,
            outputStream.toByteArray().size
        )
    }
}
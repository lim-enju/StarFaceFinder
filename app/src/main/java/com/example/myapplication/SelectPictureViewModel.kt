package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.data.model.ImageItem
import com.starFaceFinder.domain.usecase.FindFaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SelectPictureViewModel @Inject constructor(
    private val findFaceUseCase: FindFaceUseCase
): ViewModel() {
    private val INDEX_MEDIA_ID = MediaStore.MediaColumns._ID
    private val INDEX_MEDIA_URI = MediaStore.MediaColumns.DATA
    private val INDEX_ALBUM_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
    private val INDEX_DATE_ADDED = MediaStore.MediaColumns.DATE_ADDED

    private val _selectPicureList: MutableStateFlow<ArrayList<ImageItem>> = MutableStateFlow(arrayListOf())
    val selectedPictureList: StateFlow<ArrayList<ImageItem>> = _selectPicureList.asStateFlow()

    @SuppressLint("Range")
    fun fetchImageItemList(context: Context) {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            INDEX_MEDIA_ID,
            INDEX_MEDIA_URI,
            INDEX_ALBUM_NAME,
            INDEX_DATE_ADDED
        )
        val selection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Images.Media.SIZE + " > 0"
            else null
        val sortOrder = "$INDEX_DATE_ADDED DESC"
        val cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)

        cursor?.let {
            while(cursor.moveToNext()) {
                val mediaPath = cursor.getString(cursor.getColumnIndex(INDEX_MEDIA_URI))
                _selectPicureList.value.add(ImageItem(Uri.fromFile(File(mediaPath))))
            }
        }
        cursor?.close()
    }

//    fun getCheckedImageUriList(): MutableList<String> {
//        val checkedImageUriList = mutableListOf<String>()
//        imageItemList.value?.let {
//            for(imageItem in imageItemList.value!!) {
//                if(imageItem.isChecked) checkedImageUriList.add(imageItem.uri.toString())
//            }
//        }
//        return checkedImageUriList
//    }
}
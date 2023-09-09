package com.starFaceFinder.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import com.starFaceFinder.data.source.local.dao.FaceInfoDao
import com.starFaceFinder.data.source.local.dao.SimilarFacesDao

@Database(
    entities = [
        FaceInfo::class,
        SimilarFace::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun faceInfoDao(): FaceInfoDao
    abstract fun similarFacesDao(): SimilarFacesDao
}
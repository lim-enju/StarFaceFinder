package com.starFaceFinder.data.source.local

import androidx.room.Delete
import androidx.room.Insert
import com.starFaceFinder.data.model.SimilarFaces

interface SimilarFacesDao {
    @Insert
    fun insertSimilarFacesDao(similarFaces: List<SimilarFaces>)

    @Delete
    fun deleteSimilarFace(vararg fids: Int)
}
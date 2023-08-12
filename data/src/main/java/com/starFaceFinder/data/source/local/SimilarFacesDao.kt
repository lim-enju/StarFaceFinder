package com.starFaceFinder.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.starFaceFinder.data.model.SimilarFaces

@Dao
interface SimilarFacesDao {
    @Insert
    fun insertSimilarFacesDao(similarFaces: List<SimilarFaces>)

    @Query("DELETE FROM SimilarFaces WHERE fid In (:fids)")
    fun deleteSimilarFace(vararg fids: Int)
}
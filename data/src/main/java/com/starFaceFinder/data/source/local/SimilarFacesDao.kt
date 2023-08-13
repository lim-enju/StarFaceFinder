package com.starFaceFinder.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.starFaceFinder.data.model.SimilarFace

@Dao
interface SimilarFacesDao {
    @Insert
    fun insertSimilarFaceDao(similarFaces: List<SimilarFace>)

    @Query("DELETE FROM SimilarFace WHERE fid In (:fids)")
    fun deleteSimilarFace(vararg fids: Int)
}
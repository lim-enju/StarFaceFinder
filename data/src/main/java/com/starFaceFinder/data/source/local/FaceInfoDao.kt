package com.starFaceFinder.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFaces

@Dao
interface FaceInfoDao {
    //모든 히스토리 조회
    @Query("SELECT * FROM FaceInfo" +
            " INNER JOIN SimilarFaces ON SimilarFaces.fid = FaceInfo.fid")
    fun getAllFaceHistory(): Map<FaceInfo, List<SimilarFaces>>

    //히스토리 한건 조회
    @Query("SELECT * FROM FaceInfo" +
            " INNER JOIN SimilarFaces ON SimilarFaces.fid = FaceInfo.fid" +
            " WHERE FaceInfo.fid = :fid")
    fun getFaceHistory(fid:Int): Map<FaceInfo, List<SimilarFaces>>

    @Insert
    fun insertFaceInfo(faceInfo: FaceInfo)
}
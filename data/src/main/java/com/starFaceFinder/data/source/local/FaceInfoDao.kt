package com.starFaceFinder.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace

@Dao
interface FaceInfoDao {
    //모든 히스토리 조회
    @Query("SELECT * FROM FaceInfo" +
            " INNER JOIN SimilarFace ON SimilarFace.fid = FaceInfo.fid"
    )
    fun getAllFaceHistory(): Map<FaceInfo, List<SimilarFace>>

    //히스토리 한건 조회
    @Query("SELECT * FROM FaceInfo" +
            " INNER JOIN SimilarFace ON SimilarFace.fid = FaceInfo.fid" +
            " WHERE FaceInfo.fid = :fid")
    fun getFaceHistory(fid:Int): Map<FaceInfo, List<SimilarFace>>

    @Insert
    fun insertFaceInfo(faceInfo: FaceInfo): Long
}
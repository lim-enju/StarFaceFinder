package com.starFaceFinder.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import kotlinx.coroutines.flow.Flow

@Dao
interface FaceInfoDao {
    //모든 히스토리 조회
    @Query(
        "SELECT * FROM FaceInfo" +
                " INNER JOIN SimilarFace ON SimilarFace.fid = FaceInfo.fid" +
                " ORDER BY fid DESC" +
                " LIMIT :limit OFFSET :offset * :limit"
    )
    fun getFaceHistories(limit: Int, offset: Int): Map<FaceInfo, List<SimilarFace>>

    //히스토리 한건 조회
    @Query(
        "SELECT * FROM FaceInfo" +
                " INNER JOIN SimilarFace ON SimilarFace.fid = FaceInfo.fid" +
                " WHERE FaceInfo.fid = :fid"
    )
    fun getFaceHistory(fid: Long): Map<FaceInfo, List<SimilarFace>>

    //히스토리 한건 조회
    @Query(
        "SELECT * FROM FaceInfo" +
                " INNER JOIN SimilarFace ON SimilarFace.fid = FaceInfo.fid" +
                " WHERE name like '%' || :text || '%'" +
                " LIMIT :limit OFFSET :offset * :limit"
    )
    fun getSearchedFaceHistory(text: String, limit: Int, offset: Int): Map<FaceInfo, List<SimilarFace>>

    @Insert
    fun insertFaceInfo(faceInfo: FaceInfo): Long
}
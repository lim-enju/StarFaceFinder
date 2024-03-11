package com.starFaceFinder.data.source

import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace

interface IHistoryRepository {
    fun getHistoryDetail(fid: Long): Map<FaceInfo, List<SimilarFace>>

    fun getFaceHistories(limit: Int, offset: Int):  Map<FaceInfo, List<SimilarFace>>

    fun getSearchedHistories(text: String, limit: Int, offset: Int): Map<FaceInfo, List<SimilarFace>>

}
package com.example.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.starFaceFinder.data.model.FaceInfoHistory

object PagingComparator{
    object SearchedHistoriesComparator: DiffUtil.ItemCallback<FaceInfoHistory>(){
        override fun areItemsTheSame(oldItem: FaceInfoHistory, newItem: FaceInfoHistory): Boolean {
            // Id is unique.
            return newItem.faceInfo.fid == newItem.faceInfo.fid
        }

        override fun areContentsTheSame(oldItem: FaceInfoHistory, newItem: FaceInfoHistory): Boolean {
            return oldItem == newItem
        }
    }
}
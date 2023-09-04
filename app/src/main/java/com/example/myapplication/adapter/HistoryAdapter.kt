package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewHistoryBinding
import com.example.myapplication.utils.HistoryFaceInfoComparator
import com.starFaceFinder.data.model.FaceInfoHistory

class HistoryAdapter(
    var onClickHistory: (fid: Long) -> Unit,
    var onClickFavorite: (fid: Long, isFavorite: Boolean) -> Unit
) : PagingDataAdapter<FaceInfoHistory, RecyclerView.ViewHolder>(HistoryFaceInfoComparator) {

    inner class HistoryViewHolder(private val binding: ViewHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: FaceInfoHistory) {
            val faceInfo = history.faceInfo
            val similarFaces = history.similarFaces

            with(binding) {
                ageTxt.text = "${faceInfo.age}세"
                genderTxt.text = when (faceInfo.gender) {
                    "male" -> "남성"
                    "female" -> "여성"
                    "child" -> "어린이"
                    else -> null
                }
                similarTxt.text = similarFaces.firstOrNull()?.name

                Glide
                    .with(root.rootView.context)
                    .load(faceInfo.fileUri)
                    .dontAnimate()
                    .override(300, 300)
                    .into(profileImg)
            }

            binding.root.rootView.setOnClickListener {
                onClickHistory(faceInfo.fid)
            }

            binding.favoriteBtn.setOnClickListener {
                onClickFavorite(faceInfo.fid, !faceInfo.isFavorite)
                faceInfo.isFavorite = !faceInfo.isFavorite
                updateFavoriteImage(faceInfo.isFavorite)
            }
            updateFavoriteImage(faceInfo.isFavorite)
        }

        private fun updateFavoriteImage(isFavorite: Boolean) {
            val img = if (isFavorite) R.drawable.fill_favorite else R.drawable.border_favorite
            binding.favoriteBtn.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    img
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryViewHolder(
            ViewHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryViewHolder -> {
                getItem(position)?.let { item ->
                    holder.bind(item)
                }
            }
        }
    }
}
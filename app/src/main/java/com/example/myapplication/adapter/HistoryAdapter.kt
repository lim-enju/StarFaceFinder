package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewHistoryBinding
import com.example.myapplication.databinding.ViewHistoryEmptyBinding
import com.starFaceFinder.data.model.FaceInfoHistory

class HistoryAdapter(
    var histories: ArrayList<FaceInfoHistory> = arrayListOf(),
    var onClickHistory: (fid: Long) -> Unit,
    var onClickFavorite: (fid: Long, isFavorite: Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HistoryViewHolder(private val binding: ViewHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: FaceInfoHistory) {
            val faceInfo = history.faceInfo
            val similarFaces = history.similarFaceList

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
                onClickFavorite(faceInfo.fid, !history.isFavorite)
            }

            val img =
                if (history.isFavorite) R.drawable.fill_favorite else R.drawable.border_favorite
            binding.favoriteBtn.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    img
                )
            )
        }
    }

    inner class EmptyViewHolder(private val binding: ViewHistoryEmptyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when {
            histories.isNotEmpty() -> {
                HistoryViewHolder(
                    ViewHistoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> EmptyViewHolder(ViewHistoryEmptyBinding.inflate(LayoutInflater.from(parent.context)))
        }

    override fun getItemCount(): Int = histories.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryViewHolder -> {
                holder.bind(histories[position])
            }
        }
    }
}
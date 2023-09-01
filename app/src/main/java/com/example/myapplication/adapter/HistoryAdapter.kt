package com.example.myapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewHistoryBinding
import com.example.myapplication.databinding.ViewHistoryEmptyBinding
import com.starFaceFinder.data.common.TAG
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace
import java.lang.Exception

class HistoryAdapter(
    var histories: ArrayList<Pair<FaceInfo, List<SimilarFace>>> = arrayListOf(),
    var onClickHistory: (fid: Long) -> Unit,
    var onClickFavorite: (fid: Long, isFavorite: Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HistoryViewHolder(private val binding: ViewHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var fid: Long? = null
        private var isFavoriteFaceInfo: Boolean = false

        fun bind(history: Pair<FaceInfo, List<SimilarFace>>) {
            val faceInfo = history.first
            val similarFaces = history.second

            fid = faceInfo.fid

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
                Log.d(TAG, "bind: $fid $isFavoriteFaceInfo")
                fid?.let { fid -> onClickFavorite(fid, !isFavoriteFaceInfo) }
            }
        }

        fun updateFavorite(isFavorite: Boolean) {
            isFavoriteFaceInfo = isFavorite
            val img = if (isFavorite) R.drawable.fill_favorite else R.drawable.border_favorite
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
                        null,
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

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            for (payload in payloads) {
                //TODO:: payloads 에는 여러가지 타입이 올 수 있을텐데 이렇게 처리하는것이 맞는것인가
                try {
                    val favorites = payload as Set<Long>
                    if (holder is HistoryViewHolder) {
                        val isFavorite = favorites.contains(holder.fid)
                        holder.updateFavorite(isFavorite)
                        Log.d(TAG, "onBindViewHolder: ${holder.fid} ${isFavorite}")
                    }
                } catch (e: Exception) {

                }
            }
        }
    }
}
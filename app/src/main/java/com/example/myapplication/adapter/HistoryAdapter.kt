package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ViewHistoryBinding
import com.example.myapplication.databinding.ViewHistoryEmptyBinding
import com.starFaceFinder.data.model.FaceInfo
import com.starFaceFinder.data.model.SimilarFace

class HistoryAdapter(
    var histories: ArrayList<Pair<FaceInfo, List<SimilarFace>>> = arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HistoryViewHolder(private val binding: ViewHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: Pair<FaceInfo, List<SimilarFace>>) {
            val faceInfo = history.first
            val similarFaces = history.second

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
        }
    }

    inner class EmptyViewHolder(private val binding: ViewHistoryEmptyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when {
            histories.isNotEmpty() -> {
                HistoryViewHolder(ViewHistoryBinding.inflate(LayoutInflater.from(parent.context)))
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
package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.adapter.CelebritysAdapter.ViewHolderType.CELEBRITY
import com.example.myapplication.adapter.CelebritysAdapter.ViewHolderType.NOBODY
import com.example.myapplication.adapter.CelebritysAdapter.ViewHolderType.SEARCHING
import com.example.myapplication.databinding.ViewCelebrityBinding
import com.example.myapplication.databinding.ViewNobodyCelebrityBinding
import com.example.myapplication.databinding.ViewSearchingCelebrityBinding
import com.starFaceFinder.data.model.SimilarFace
import kotlin.math.round

class CelebritysAdapter(
    var celebrities: List<SimilarFace> = listOf(),
    var isSearchingComplete: Boolean = false
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewHolderType(val type: Int){
        SEARCHING(0),   //아직 검색중인 경우
        CELEBRITY(1),   //유명인 검색 결과가 있는 경우
        NOBODY(2)       //유사한 유명인이 없는 경우
    }

    inner class CelebrityViewHolder(private val binding: ViewCelebrityBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(celebrity: SimilarFace?){
            val confidence = (celebrity?.similarConfidence ?: 0.0) * 100
            val roundConfidence = round(confidence*100) / 100

            binding.confidenceTxt.text = "일치율: $roundConfidence %"
            binding.confidenceProgressBar.progress = roundConfidence.toInt()
            binding.nameTxt.text = celebrity?.name

            Glide
                .with(binding.root.rootView.context)
                .load(celebrity?.link)
                .dontAnimate()
                .override(300,400)
                .into(binding.similarImg)
        }
    }

    inner class NobodyCelebrityViewHolder(binding: ViewNobodyCelebrityBinding): RecyclerView.ViewHolder(binding.root)

    inner class SearchingViewHolder(binding: ViewSearchingCelebrityBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
        = when(viewType) {

        CELEBRITY.type -> {
            val view = ViewCelebrityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CelebrityViewHolder(view)
        }

        NOBODY.type -> {
            val view = ViewNobodyCelebrityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NobodyCelebrityViewHolder(view)
        }
        else -> {
            val view = ViewSearchingCelebrityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SearchingViewHolder(view)
        }
    }

    override fun getItemCount(): Int = celebrities.size.coerceAtLeast(1)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CelebrityViewHolder -> {
                holder.bind(celebrities[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when{
            //아직 검색중인 경우
            !isSearchingComplete -> SEARCHING.type
            //유명인을 찾은 경우
            celebrities.isNotEmpty() -> CELEBRITY.type
            //유명인이 없는 경우
            else -> NOBODY.type
        }
}
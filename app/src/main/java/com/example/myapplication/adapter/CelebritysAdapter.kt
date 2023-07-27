package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ViewCelebrityBinding
import com.starFaceFinder.data.model.Celebrity
import com.starFaceFinder.data.model.Faces

class CelebritysAdapter(
    var celebrities: ArrayList<Faces> = arrayListOf()
): RecyclerView.Adapter<CelebritysAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ViewCelebrityBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(celebrity: Celebrity?){
            binding.nameTxt.text = celebrity?.value
            binding.confidenceTxt.text = (celebrity?.confidence?:0).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewCelebrityBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = celebrities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = celebrities[position]
        holder.bind(data.celebrity)
    }
}
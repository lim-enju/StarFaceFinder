package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewImageBinding
import com.starFaceFinder.data.model.ImageItem

class ImageAdapter(
    var images: ArrayList<ImageItem> = arrayListOf()
): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ViewImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: ImageItem){
            binding.image.setImageURI(image.uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewImageBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = images[position]
        holder.bind(data)
    }
}
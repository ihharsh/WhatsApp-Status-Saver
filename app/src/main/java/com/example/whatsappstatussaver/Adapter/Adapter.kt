package com.example.whatsappstatussaver.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsappstatussaver.Model.StoryModel
import com.example.whatsappstatussaver.databinding.ImageFragStoryItemBinding


class Adapter(ImageList: ArrayList<StoryModel>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var imageList = ImageList
    inner class ViewHolder(val binding: ImageFragStoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           ImageFragStoryItemBinding.
           inflate(LayoutInflater.from(parent.context),
           parent,
               false)
       )
    }

    override fun getItemCount(): Int {
       return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        var imageUri = imageList[position].uri
        var imageView = holder.binding.ivImageFrag

        Glide.with(holder.itemView.context).load(imageUri).into(imageView)

    }
}
package com.example.whatsappstatussaver.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsappstatussaver.Activity.ImageDisplay
import com.example.whatsappstatussaver.Activity.VideoDisplay
import com.example.whatsappstatussaver.Model.StoryModel
import com.example.whatsappstatussaver.databinding.RvStoryItemBinding


class Adapter(ImageList: ArrayList<StoryModel>,private val clickListener:(StoryModel,View)->Unit) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var imageList = ImageList
    inner class ViewHolder(val binding: RvStoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           RvStoryItemBinding.
           inflate(LayoutInflater.from(parent.context),
           parent,
               false)
       )
    }

    override fun getItemCount(): Int {
       return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var story = imageList[position]

        var imageUri = imageList[position].uri
        var imageView = holder.binding.ivStatusImage

        imageView.setOnClickListener {

            if(story.filename.endsWith(".jpg")) {
                onClickImage(story,holder.itemView.context)
            } else {
                onClickVideo(story,holder.itemView.context)

            }

            imageView.setOnLongClickListener {
                clickListener(story,it)
                return@setOnLongClickListener true
            }


        }

        if(story.filename.endsWith(".mp4")) {
            holder.binding.cvVideoThumbnail.visibility = View.VISIBLE
        }

        Glide.with(holder.itemView.context).load(imageUri).into(imageView)

    }

    private fun onClickImage(image: StoryModel,context: Context){

        var intent = Intent(context,ImageDisplay::class.java)
        intent.putExtra("IMAGE_URI",image.uri.toString())
        context.startActivity(intent)


    }

    private fun onClickVideo(image: StoryModel,context: Context){

        var intent = Intent(context,VideoDisplay::class.java)
        intent.putExtra("VIDEO_URI",image.uri.toString())
        context.startActivity(intent)


    }


}
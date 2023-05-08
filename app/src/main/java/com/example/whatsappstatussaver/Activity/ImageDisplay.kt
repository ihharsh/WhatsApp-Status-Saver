package com.example.whatsappstatussaver.Activity

import android.content.Intent
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.bumptech.glide.Glide
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.databinding.ActivityImageDisplayBinding

class ImageDisplay : AppCompatActivity() {

    lateinit var binding: ActivityImageDisplayBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var imageUri = intent.getStringExtra("IMAGE_URI")

        var imageView = binding.ivImageDisplay


        Glide.with(this).load(imageUri).into(imageView)


    }






}










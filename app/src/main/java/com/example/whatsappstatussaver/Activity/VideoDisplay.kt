package com.example.whatsappstatussaver.Activity

import android.media.session.MediaSession

import android.widget.MediaController

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatsappstatussaver.databinding.ActivityVideoDisplayBinding

class VideoDisplay : AppCompatActivity() {

    lateinit var binding: ActivityVideoDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoDisplayBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var videoView = binding.vvVideoDisplay

        var videoUri = intent.getStringExtra("VIDEO_URI")
        videoView.setVideoURI(Uri.parse(videoUri))



        val mediaController = MediaController(this)

        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.start()





    }
}



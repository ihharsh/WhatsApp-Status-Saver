package com.example.whatsappstatussaver.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsappstatussaver.Adapter.Adapter
import com.example.whatsappstatussaver.Model.StoryModel
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.databinding.FragmentVideoBinding


class VideoFragment(videoList: ArrayList<StoryModel>) : Fragment() {

     lateinit var binding: FragmentVideoBinding
     var videoList = videoList
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVideoBinding.inflate(inflater,container,false)

        var adapter = Adapter(videoList)
        binding.rvVideoFrag.layoutManager = GridLayoutManager(context,2)
        binding.rvVideoFrag.adapter = adapter



        return binding.root
    }


}
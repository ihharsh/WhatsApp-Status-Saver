package com.example.whatsappstatussaver.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsappstatussaver.Adapter.Adapter
import com.example.whatsappstatussaver.Model.StoryModel
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.databinding.FragmentImageBinding


class ImageFragment(ImageList: ArrayList<StoryModel>) : Fragment() {

    lateinit var binding: FragmentImageBinding

    var imageList = ImageList


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater, container, false)

        var adapter = Adapter(imageList)
        binding.rvImageFrag.layoutManager = GridLayoutManager(context,2)
        binding.rvImageFrag.adapter = adapter





        return binding.root
    }


}
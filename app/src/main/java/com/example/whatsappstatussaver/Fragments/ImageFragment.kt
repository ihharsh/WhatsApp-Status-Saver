package com.example.whatsappstatussaver.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.databinding.FragmentImageBinding


class ImageFragment() : Fragment() {

    lateinit var binding: FragmentImageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater, container, false)





        return binding.root
    }


}
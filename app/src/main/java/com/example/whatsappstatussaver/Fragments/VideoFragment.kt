package com.example.whatsappstatussaver.Fragments

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsappstatussaver.Adapter.Adapter
import com.example.whatsappstatussaver.Model.StoryModel
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.databinding.FragmentVideoBinding
import java.io.IOException
import java.io.OutputStream


class VideoFragment(videoList: ArrayList<StoryModel>) : Fragment() {

     lateinit var binding: FragmentVideoBinding
     var videoList = videoList
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVideoBinding.inflate(inflater,container,false)

        var adapter = Adapter(videoList){
                selectedStatusItem,view->listItemClicked(selectedStatusItem,view)
        }
        binding.rvVideoFrag.layoutManager = GridLayoutManager(context,2)
        binding.rvVideoFrag.adapter = adapter



        return binding.root
    }

    private fun listItemClicked(selectedStatusItem: StoryModel,view:View) {

        val menu = PopupMenu(view.context,view)
        menu.inflate(R.menu.share_download_menu)

        menu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.download -> {downloadStatus(selectedStatusItem)}

                R.id.share -> {}

            }

            return@setOnMenuItemClickListener true
        }

        menu.show()

        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menuPopup = popup.get(menu)
        menuPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menuPopup, true)


    }

    private fun downloadStatus(story: StoryModel) {

        val inputStream = context?.contentResolver?.openInputStream(story.uri!!)
        val fileName = "${System.currentTimeMillis()}.mp4"

        try{
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME,fileName)
            values.put(MediaStore.MediaColumns.MIME_TYPE,"video/mp4")
            values.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_DOCUMENTS+"/Status Saver/")

            val uri = context?.contentResolver?.insert(
                MediaStore.Files.getContentUri("external"),
                values
            )

            val outputstream: OutputStream? = uri?.let { context?.contentResolver?.openOutputStream(it) }

            if(inputStream!=null){
                outputstream?.write(inputStream.readBytes())
            }

            outputstream?.close();
            Toast.makeText(context,"Status Saved",Toast.LENGTH_SHORT).show()


        }catch (e:IOException) {
            Toast.makeText(context,"FAILED",Toast.LENGTH_SHORT).show()
        }




    }


}
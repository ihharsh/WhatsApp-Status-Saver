package com.example.whatsappstatussaver.Fragments

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsappstatussaver.Adapter.Adapter
import com.example.whatsappstatussaver.Model.StoryModel
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.databinding.FragmentImageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class ImageFragment(ImageList: ArrayList<StoryModel>) : Fragment() {

    lateinit var binding: FragmentImageBinding

    var imageList = ImageList


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater, container, false)

        var adapter = Adapter(imageList){
                selectedStatusItem,view->listItemClicked(selectedStatusItem,view)
        }
        
        binding.rvImageFrag.layoutManager = GridLayoutManager(context,2)
        binding.rvImageFrag.adapter = adapter





        return binding.root
    }

    private fun listItemClicked(selectedStatusItem: StoryModel,view:View) {

        val menu = PopupMenu(view.context,view)
        menu.inflate(R.menu.share_download_menu)

        menu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.download -> {downloadStatus(selectedStatusItem)}

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

        var bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,
            story.uri
        )

        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            context?.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM+"/Status Saver/")
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context,"Status Saved", Toast.LENGTH_SHORT).show()
        }




    }


}
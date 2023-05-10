package com.example.whatsappstatussaver.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.storage.StorageManager
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.whatsappstatussaver.Fragments.ImageFragment
import com.example.whatsappstatussaver.Fragments.SavedFragment
import com.example.whatsappstatussaver.Fragments.VideoFragment
import com.example.whatsappstatussaver.Model.StoryModel
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private val REQUEST_CODE_PICK_FOLDER = 1
    lateinit var stories_images: ArrayList<StoryModel>
    lateinit var stories_videos: ArrayList<StoryModel>

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        stories_images = ArrayList()
        stories_videos = ArrayList()

        var bn = binding.bottomN




        SetNavIcons(bn)

        val result = readDataFromShPref()

        if(result) {

            val sharedpref = getSharedPreferences("DATA_PATH", MODE_PRIVATE)
            val folderUri = sharedpref.getString("PATH","")


            contentResolver.takePersistableUriPermission(Uri.parse(folderUri),Intent.FLAG_GRANT_READ_URI_PERMISSION)


            val folder = DocumentFile.fromTreeUri(application, Uri.parse(folderUri)) ?: return

            stories_images = ArrayList<StoryModel>()
            folder.listFiles().forEach { file ->
                val story = StoryModel(
                    path = file.uri.toString(),
                    filename = file.name ?: "",
                    uri = file.uri
                )
                if(story.filename.endsWith(".jpg")){
                    stories_images.add(story)
                } else if(story.filename.endsWith(".mp4")) {
                    stories_videos.add(story)
                }

            }

            loadFragment(ImageFragment(stories_images))



        } else {
            getFolderPermission()
        }

       // getFolderPermission()

        bn.show(1)
        bn.setOnClickMenuListener {model ->

           var id = model.id

            if(id== 1) {
                loadFragment(ImageFragment(stories_images))
            } else if (id==2) {
                loadFragment(VideoFragment(stories_videos))

            } else if(id==3){
                loadFragment(SavedFragment())

            }
        }









    }

    private fun readDataFromShPref(): Boolean {

        val sh = getSharedPreferences("DATA_PATH", MODE_PRIVATE)
        val uriPath = sh.getString("PATH","")

        if(uriPath!=null) {
            if(uriPath.isEmpty()){
                return false
            }
        }

        return true

    }

    private fun SetNavIcons(bn: MeowBottomNavigation) {
        bn.add(MeowBottomNavigation.Model(1, R.drawable.image_vector))
        bn.add(MeowBottomNavigation.Model(2, R.drawable.video_vector))
        bn.add(MeowBottomNavigation.Model(3, R.drawable.save_vector))
    }

    private fun loadFragment(fragment : Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getFolderPermission() {

        val storageManager = application.getSystemService(Context.STORAGE_SERVICE) as StorageManager

        val intent = storageManager.primaryStorageVolume.createOpenDocumentTreeIntent()

        val targetDirectory = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2EStatuses"

        var uri = intent.getParcelableExtra<Uri>("android.provider.extra.INITIAL_URI") as Uri

        var scheme = uri.toString()
        scheme = scheme.replace("/root/", "/tree/")
        scheme += "%3A$targetDirectory"
        uri = Uri.parse(scheme)

        intent.putExtra("android.provider.extra.INITIAL_URI", uri)
        intent.putExtra("android.content.extra.SHOW_ADVANCED", true)


        startActivityForResult(intent, REQUEST_CODE_PICK_FOLDER)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_FOLDER && resultCode == Activity.RESULT_OK) {

            val folderUri = data?.data ?: return



            val sharedPref = getSharedPreferences("DATA_PATH", MODE_PRIVATE)
            val edit = sharedPref.edit()
            edit.putString("PATH",folderUri.toString())
            edit.apply()




            contentResolver.takePersistableUriPermission(folderUri,Intent.FLAG_GRANT_READ_URI_PERMISSION)


            val folder = DocumentFile.fromTreeUri(application, folderUri) ?: return

             stories_images = ArrayList<StoryModel>()
            folder.listFiles().forEach { file ->
                val story = StoryModel(
                    path = file.uri.toString(),
                    filename = file.name ?: "",
                    uri = file.uri
                )
                if(story.filename.endsWith(".jpg")){
                    stories_images.add(story)
                } else if(story.filename.endsWith(".mp4")) {
                    stories_videos.add(story)
                }

            }

            loadFragment(ImageFragment(stories_images))

    }


}

}
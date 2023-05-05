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
import com.example.whatsappstatussaver.Fragments.ImageFragment
import com.example.whatsappstatussaver.Fragments.SavedFragment
import com.example.whatsappstatussaver.Fragments.VideoFragment
import com.example.whatsappstatussaver.Model.StoryModel
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private val REQUEST_CODE_PICK_FOLDER = 1

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(ImageFragment())

        binding.bottomNav.setOnNavigationItemSelectedListener { item->

            var id = item.itemId

            if(id== R.id.nav_image) {
                loadFragment(ImageFragment())
            } else if (id==R.id.nav_video) {
                loadFragment(VideoFragment())

            } else if(id==R.id.nav_saved){
                loadFragment(SavedFragment())

            }


            return@setOnNavigationItemSelectedListener true
        }


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
//            binding?.tv?.text = folderUri.toString()


            contentResolver.takePersistableUriPermission(folderUri,Intent.FLAG_GRANT_READ_URI_PERMISSION)


            val folder = DocumentFile.fromTreeUri(application, folderUri) ?: return

            val stories = mutableListOf<StoryModel>()
            folder.listFiles().forEach { file ->
                val story = StoryModel(
                    path = file.uri.toString(),
                    filename = file.name ?: "",
                    uri = file.uri
                )
                stories.add(story)
            }

    }


}

}
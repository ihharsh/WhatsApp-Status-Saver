package com.example.whatsappstatussaver

import android.net.Uri
import android.os.Environment
import java.io.File

class viewModel {

    private var storyList : ArrayList<StoryModel>? = null

    fun getStoryData() : ArrayList<StoryModel> {

        var f = StoryModel()

        var targetPath = Environment.getExternalStorageDirectory().absolutePath+Constants.FOLDER_NAME

        var targetDir = File(targetPath)

        var files = targetDir.listFiles();

        for (file in files) {
            f.uri = Uri.fromFile(file)
            f.path = file.absolutePath
            f.filename = file.name

            if(!f.uri.toString().endsWith(".nomedia")) {
                storyList?.add(f)
            }

        }

        return storyList!!


    }
}
package com.example.whatsappstatussaver

import android.net.Uri

data class StoryModel(
    var path:String = "",
    var filename: String ="",
    var uri: Uri? = null
     )

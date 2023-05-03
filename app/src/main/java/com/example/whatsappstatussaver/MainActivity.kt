package com.example.whatsappstatussaver

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    val REQUEST_CODE = 1
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission() {
       if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
       {
           // main code
       } else {
           ActivityCompat.requestPermissions(this,
               arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_CODE)
       }
    }
}
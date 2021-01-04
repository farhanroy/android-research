package com.camerax.basic.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.camerax.basic.BuildConfig
import com.camerax.basic.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun debug(message: String){
    if (BuildConfig.DEBUG) Log.d("Result", message)
}

fun Activity.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun getOutputDirectory(context: Context): File {
    val appContext = context.applicationContext
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())mediaDir else appContext.filesDir
}

fun createFile(baseFolder: File, format: String, extension: String) =
    File(baseFolder, SimpleDateFormat(format, Locale.US)
        .format(System.currentTimeMillis()) + extension)


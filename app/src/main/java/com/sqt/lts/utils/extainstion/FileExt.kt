package com.example.lts.utils.extainstion

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.createFile():File{
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(imageFileName, ".jpg", externalCacheDir)
}

fun createUniqueImageFileUri(context: Context): Uri {
    val timestamp = System.currentTimeMillis() // Unique file name based on timestamp
    val imageFile = File(context.cacheDir, "camera_image_$timestamp.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}

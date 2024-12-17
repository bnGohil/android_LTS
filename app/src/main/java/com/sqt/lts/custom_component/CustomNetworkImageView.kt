package com.sqt.lts.custom_component

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sqt.lts.R
import com.sqt.lts.ui.theme.kPrimaryColor
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun CustomNetworkImageView(
    modifier:Modifier,
    imagePath: String?=null,
    contentScale:ContentScale
) {


    var isLoading = remember { mutableStateOf<Boolean>(false) }


    Box {

        if(isLoading.value){
            CircularProgressIndicator(
                trackColor = kPrimaryColor,
                strokeWidth = 1.dp,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }

        Image(
            contentScale = contentScale,
            modifier = modifier,
            contentDescription= "custom_image",
            painter = if(imagePath != null && imagePath != "") rememberAsyncImagePainter(
                imagePath,
                error = painterResource(id = R.drawable.placeholder_error),
                onLoading = { isLoading.value = true },
                onSuccess = {isLoading.value = false},
                onError = { isLoading.value = false },


                )else painterResource(id = R.drawable.placeholder_error)
        )
    }


}
fun createTempImageFile(imagePath: String,context: Context, imageBytes: ByteArray): String? {
    return try {
        // Create a temp file in the cache directory
        val tempFile = File.createTempFile(imagePath, ".jpg", context.cacheDir)
        FileOutputStream(tempFile).use { outputStream ->
            outputStream.write(imageBytes)
        }
        // Return the absolute path of the file
        tempFile.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
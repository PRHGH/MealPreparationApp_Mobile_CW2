package com.example.mealpreparationapp

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

private val ByteArraySaver = listSaver<ByteArray?, Int>(
    save = { bytes -> bytes?.map { it.toInt() and 0xFF } ?: emptyList() },
    restore = { ints ->
        if (ints.isEmpty()) null
        else ByteArray(ints.size) { index -> ints[index].toByte() }
    }
)

@Composable
fun MealThumbnail(
    imageUrl: String?,
    mealName: String?,
    modifier: Modifier = Modifier.size(80.dp)
) {
    var imageBytes by rememberSaveable(imageUrl, stateSaver = ByteArraySaver) {
        mutableStateOf<ByteArray?>(null)
    }

    var loadFailed by rememberSaveable(imageUrl) {
        mutableStateOf(false)
    }

    LaunchedEffect(imageUrl) {
        if (imageUrl.isNullOrBlank()) {
            loadFailed = true
            imageBytes = null
            return@LaunchedEffect
        }

        if (imageBytes == null) {
            val downloaded = downloadImageBytes(imageUrl)
            if (downloaded != null) {
                imageBytes = downloaded
                loadFailed = false
            } else {
                loadFailed = true
            }
        }
    }

    when {
        imageBytes != null -> {
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes!!.size)
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = mealName ?: "Meal image",
                    modifier = modifier,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            } else {
                Box(modifier = modifier, contentAlignment = Alignment.Center) {
                    Text("No Image")
                }
            }
        }

        loadFailed -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text("No Image")
            }
        }

        else -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
        }
    }
}

suspend fun downloadImageBytes(urlString: String): ByteArray? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.doInput = true
            connection.connect()

            val inputStream = connection.inputStream
            val output = ByteArrayOutputStream()

            val buffer = ByteArray(4096)
            var bytesRead = inputStream.read(buffer)

            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead)
                bytesRead = inputStream.read(buffer)
            }

            inputStream.close()
            connection.disconnect()

            output.toByteArray()
        } catch (e: Exception) {
            null
        }
    }
}
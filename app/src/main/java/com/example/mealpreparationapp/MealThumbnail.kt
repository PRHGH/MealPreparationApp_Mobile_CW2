package com.example.mealpreparationapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun MealThumbnail(
    imageUrl: String?,
    mealName: String?
) {
    var bitmap by remember(imageUrl) { mutableStateOf<Bitmap?>(null) }
    var loadFailed by remember(imageUrl) { mutableStateOf(false) }

    LaunchedEffect(imageUrl) {
        bitmap = null
        loadFailed = false

        if (imageUrl.isNullOrBlank()) {
            loadFailed = true
            return@LaunchedEffect
        }

        bitmap = loadBitmapFromUrl(imageUrl)
        if (bitmap == null) {
            loadFailed = true
        }
    }

    when {
        bitmap != null -> {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = mealName ?: "Meal image",
                modifier = Modifier.size(80.dp)
            )
        }

        loadFailed -> {
            Text("No Image")
        }

        else -> {
            Text("Loading...")
        }
    }
}

suspend fun loadBitmapFromUrl(urlString: String): Bitmap? {
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
            val bitmap = BitmapFactory.decodeStream(inputStream)

            inputStream.close()
            connection.disconnect()

            bitmap
        } catch (e: Exception) {
            null
        }
    }
}
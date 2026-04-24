package com.example.mealpreparationapp

// Networking utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object NetworkUtils {

    // API call: URL -> String response
    suspend fun getUrlContent(urlString: String): String {
        // No network on main thread (Dispatcher.IO)
        return withContext(Dispatchers.IO) {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val response = StringBuilder()

            try {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = reader.readLine()

                while (line != null) {
                    response.append(line)
                    line = reader.readLine()
                }

                reader.close()
                response.toString()
            } finally {
                connection.disconnect()
            }
        }
    }

    // URL safe encoding
    fun encode(text: String): String {
        return URLEncoder.encode(text, "UTF-8")
    }
}

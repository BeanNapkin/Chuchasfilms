package pro.fateeva.chuchasfilms

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object FilmsLoader {
    fun loadFilms(): FilmListDTO? {
            val uri = URL("https://api.themoviedb.org/3/discover/movie?api_key=6db29454ef8c14f049f3447e4f978c60")

            lateinit var urlConnection: HttpsURLConnection
            return try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                // преобразование ответа от сервера (JSON) в модель данных (WeatherDTO)
                val lines = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                Gson().fromJson(lines, FilmListDTO::class.java)
            }  finally {
                urlConnection.disconnect()
            }
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }

        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}
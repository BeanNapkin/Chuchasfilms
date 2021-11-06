package pro.fateeva.chuchasfilms

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object FilmsLoader {
    private const val API_KEY = "6db29454ef8c14f049f3447e4f978c60"
    private val API = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(FilmAPI::class.java)

    fun loadFilms(): FilmListDTO? {
       return API.getFilmList(API_KEY).execute().body()
    }
}
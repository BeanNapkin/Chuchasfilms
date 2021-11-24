package pro.fateeva.chuchasfilms

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreListDTO
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
    private const val FILM_COVER_SIZE = "w300"
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

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

    fun loadGenres(): GenreListDTO {
        return API.getGenreList(API_KEY).execute().body() ?: GenreListDTO(emptyList())
    }

    fun generateImagePath(path: String):String {
        return "${IMAGE_BASE_URL}${FILM_COVER_SIZE}${path}"
    }

}
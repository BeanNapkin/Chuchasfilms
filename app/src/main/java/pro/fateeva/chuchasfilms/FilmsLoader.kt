package pro.fateeva.chuchasfilms

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pro.fateeva.chuchasfilms.rest_entities.FilmDTO
import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreListDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object FilmsLoader {
    private const val API_KEY = "6db29454ef8c14f049f3447e4f978c60"
    private const val FILM_COVER_SIZE = "w300"
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    private val retrofit: Retrofit
    private val API: FilmAPI

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging) // <-- this is the important line!

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .client(httpClient.build())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
            API = retrofit.create(FilmAPI::class.java)
    }

    fun loadFilms(movieTopList: MovieTopList): FilmListDTO? {
       return API.getFilmList(movieTopList.value, API_KEY).execute().body()
    }

    fun loadFilmById(id: Long): FilmDTO? {
        return API.getFilm(id, API_KEY).execute().body()
    }

    fun loadGenres(): GenreListDTO {
        return API.getGenreList(API_KEY).execute().body() ?: GenreListDTO(emptyList())
    }

    fun generateImagePath(path: String):String {
        return "${IMAGE_BASE_URL}${FILM_COVER_SIZE}${path}"
    }

}
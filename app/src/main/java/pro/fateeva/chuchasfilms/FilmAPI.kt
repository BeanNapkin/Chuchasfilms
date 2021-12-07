package pro.fateeva.chuchasfilms

import com.google.gson.annotations.SerializedName
import pro.fateeva.chuchasfilms.rest_entities.FilmDTO
import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmAPI {
    @GET("3/movie/{top}")
    fun getFilmList(
        @Path("top") movieTopList: String,
        @Query("api_key") apiKey: String
    ): Call<FilmListDTO>

    @GET("/3/genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey: String
    ): Call<GenreListDTO>

    @GET("/3/movie/{movie_id}")
    fun getFilm(
        @Path("movie_id") id: Long,
        @Query("api_key") apiKey: String
    ): Call<FilmDTO>
}

enum class MovieTopList(val value: String) {
    POPULAR("popular"),
    UPCOMING("upcoming"),
    NOW_PLAYING("now_playing")
}
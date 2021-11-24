package pro.fateeva.chuchasfilms

import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmAPI {
    @GET("3/discover/movie")
    fun getFilmList(
        @Query("api_key") apiKey: String
    ): Call<FilmListDTO>

    @GET("/3/genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey: String
    ): Call<GenreListDTO>
}
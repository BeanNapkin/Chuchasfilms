package pro.fateeva.chuchasfilms.ui.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Film(
    val id: Long,
    val title: String? = null,
    val isAdult: Boolean? = null,
    val year: String? = null,
    val rating: String? = null,
    val genres: List<String>? = null,
    val description: String? = null,
    val posterPath: String? = null,
    val noteAboutFilm: String? = null,
    val viewDate: Date? = null
) : Parcelable



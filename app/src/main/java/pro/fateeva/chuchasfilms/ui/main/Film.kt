package pro.fateeva.chuchasfilms.ui.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val title: String? = null,
    val isAdult: Boolean? = null,
    val year: String? = null,
    val rating: String? = null,
    val genres: List<String>? = null,
    val description: String? = null,
    val cast: String? = null,
    val posterPath: String? = null
) : Parcelable



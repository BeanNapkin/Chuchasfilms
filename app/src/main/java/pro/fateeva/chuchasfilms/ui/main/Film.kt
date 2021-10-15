package pro.fateeva.chuchasfilms.ui.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val title: String?,
    val year: String?,
    val rating: String?,
    val genre: String?,
    val description: String?,
    val cast: String?
): Parcelable



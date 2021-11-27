package pro.fateeva.chuchasfilms.data

import android.content.Context

interface PreferencesRepository {
    fun saveAdultFilmsPreference(context: Context, isAdultFilmsCanBeShown: Boolean)
    fun getAdultFilmsPreference(context: Context): Boolean
}
package pro.fateeva.chuchasfilms.data

import android.content.Context

class PreferencesRepositoryImpl : PreferencesRepository {

     companion object{
         const val general =  "GENERAL"
         const val isAdultFilmsCanBeShownKey = "isAdultFilmsCanBeShown"
     }

    override fun saveAdultFilmsPreference(context: Context, isAdultFilmsCanBeShown: Boolean) {
        val sharedPref = context.getSharedPreferences(general, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(isAdultFilmsCanBeShownKey, isAdultFilmsCanBeShown)
        editor.apply()
    }

    override fun getAdultFilmsPreference(context: Context) : Boolean {
        return context.getSharedPreferences(general, Context.MODE_PRIVATE).getBoolean(isAdultFilmsCanBeShownKey, false)
    }
}
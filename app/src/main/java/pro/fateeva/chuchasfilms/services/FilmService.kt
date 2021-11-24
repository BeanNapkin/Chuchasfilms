package pro.fateeva.chuchasfilms.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import pro.fateeva.chuchasfilms.FilmsLoader
import pro.fateeva.chuchasfilms.data.FilmMapper
import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO

class FilmService : IntentService("FilmService") {

    private val mapper = FilmMapper()

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_LOAD_FILMS -> {
                handleLoadFilms()
            }
        }
    }

    private fun handleLoadFilms() {
        sendBack(FilmsLoader.loadFilms())
    }

    private fun sendBack(filmListDTO: FilmListDTO?) {
        val broadcastIntent = Intent(BROADCAST_ACTION)
        broadcastIntent.putExtra(EXTRA_DATA, mapper.map(filmListDTO).toTypedArray())
        sendBroadcast(broadcastIntent)
    }


    companion object {
        private const val ACTION_LOAD_FILMS = "pro.fateeva.chuchasfilms.services.action.LOAD_FILMS"
        const val EXTRA_DATA = "pro.fateeva.chuchasfilms.services.extra.DATA"
        const val BROADCAST_ACTION = "BROADCAST_ACTION"

        @JvmStatic
        fun startActionLoadFilms(context: Context) {
            val intent = Intent(context, FilmService::class.java).apply {
                action = ACTION_LOAD_FILMS
            }
            context.startService(intent)
        }

    }
}

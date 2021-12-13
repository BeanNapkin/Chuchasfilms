package pro.fateeva.chuchasfilms.geofences

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    val handler = GeofenceIntentHandler(context)
    handler.handleIntent(intent)
  }
}
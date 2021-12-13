package pro.fateeva.chuchasfilms.geofences

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class ReminderRepository(private val context: Context) {

    private val geofencingClient = LocationServices.getGeofencingClient(context)
    private val reminders = mutableListOf<Reminder>()

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun get(requestId: String) = reminders.firstOrNull { it.id == requestId }

    @SuppressLint("MissingPermission")
    fun add(
        reminder: Reminder,
        success: () -> Unit,
        failure: (error: String) -> Unit
    ) {
        val geofence = buildGeofence(reminder)
        geofencingClient
            .addGeofences(buildGeofencingRequest(geofence), geofencePendingIntent)
            .addOnSuccessListener {
                reminders.add(reminder)
                success()
            }
            .addOnFailureListener {
                failure("Error")
                it.printStackTrace()
            }
    }

    private fun buildGeofence(reminder: Reminder): Geofence {
        val latitude = reminder.latLng.latitude
        val longitude = reminder.latLng.longitude
        val radius = reminder.radius

        return Geofence.Builder()
            .setRequestId(reminder.id)
            .setCircularRegion(
                latitude,
                longitude,
                radius.toFloat()
            )
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    private fun buildGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(0)
            .addGeofences(listOf(geofence))
            .build()
    }

    companion object {
        private const val PREFS_NAME = "ReminderRepository"
        private const val REMINDERS = "REMINDERS"
    }

}
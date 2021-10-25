package pro.fateeva.chuchasfilms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import com.google.android.material.snackbar.Snackbar
import pro.fateeva.chuchasfilms.ui.main.SnackbarExtensions.showSnackbar

class ConnectionBroadcastReceiver(private val view: View): BroadcastReceiver() {

    private var snackbar: Snackbar? = null

    override fun onReceive(context: Context, intent: Intent) {
        val info = intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
        if (info?.isConnected == false){
            snackbar = view.showSnackbar(R.string.no_connection)
        } else {
            snackbar?.dismiss()
            snackbar = null
        }
    }
}
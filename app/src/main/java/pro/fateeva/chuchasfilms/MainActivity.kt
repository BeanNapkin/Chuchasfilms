package pro.fateeva.chuchasfilms

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pro.fateeva.chuchasfilms.geofences.Reminder
import pro.fateeva.chuchasfilms.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var receiver: ConnectionBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
        receiver = ConnectionBroadcastReceiver(findViewById(R.id.container))
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
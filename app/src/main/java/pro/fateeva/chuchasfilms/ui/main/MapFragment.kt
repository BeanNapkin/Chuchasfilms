package pro.fateeva.chuchasfilms.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pro.fateeva.chuchasfilms.R

class MapFragment : Fragment() {

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                getLocation()
            } else {
                Toast.makeText(context, "Need permission", Toast.LENGTH_SHORT)
            }
        }

    companion object {
        fun newInstance() = MapFragment()
    }

    private val callback = OnMapReadyCallback { googleMap ->
       val locationListener = LocationListener {
//            googleMap.addMarker(MarkerOptions().position(it.latitude, it.longitude).title("You are here"))
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it.latitude, it.longitude))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        checkPermission()
    }

    private fun checkPermission() {
        requestPermission()
    }

    private fun requestPermission() {
        permissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun getLocation() {
//        activity?.let { context ->
//            if (ContextCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//
//                val locationManager =
//                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
//                    provider?.let {
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
//                            0f, locationListener);
//                    }
//                } else {
//                    val location =
//                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//                    if (location == null) {
//                        Toast.makeText(context, "No lastknown location", Toast.LENGTH_SHORT)
//                    } else {
//                        Toast.makeText(context, "GPS turned off", Toast.LENGTH_SHORT)
//                    }
//                }
//            } else {
//                Toast.makeText(context, "Need permission", Toast.LENGTH_SHORT)
//            }
//        }
    }

}
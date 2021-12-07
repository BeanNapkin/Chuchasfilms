package pro.fateeva.chuchasfilms.ui.main

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.map_fragment.*
import pro.fateeva.chuchasfilms.R
import pro.fateeva.chuchasfilms.app.App
import pro.fateeva.chuchasfilms.databinding.MapFragmentBinding
import pro.fateeva.chuchasfilms.geofences.Reminder
import java.io.IOException

class MapFragment : Fragment() {

    private var _binding: MapFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
    }

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allPermissionsGranted = true

            for ((_, value) in result) {
                if (value.not()) {
                    allPermissionsGranted = false
                }
            }

            if (!allPermissionsGranted) {
                Toast.makeText(context, "Need permission", Toast.LENGTH_SHORT)
            }
        }

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MapFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        checkPermission()

        initialSearchAddress()

        val reminder = Reminder(latLng = LatLng(55.504251,36.0299415), message = "Сходи в кино!", radius = 1000.0)
        App.getRepository().add(reminder,{}, {})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermission() {
        requestPermission()
    }

    private fun requestPermission() {
        permissionResult.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        )
    }

    private fun initialSearchAddress() {
        binding.searchButton.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = searchEditText.text.toString()
            Thread {
                try {
                    val addresses = geoCoder.getFromLocationName(searchText, 1)
                    if (addresses.size > 0) {
                        goToAddress(addresses, it, searchText)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun goToAddress(
        addresses: MutableList<Address>,
        view: View,
        searchText: String
    ) {
        val location = LatLng(
            addresses[0].latitude,
            addresses[0].longitude
        )
        view.post {
            setMarker(location, searchText)
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    17f
                )
            )
        }
    }

    private fun setMarker(
        location: LatLng,
        searchText: String
    ): Marker? {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
        )
    }

}
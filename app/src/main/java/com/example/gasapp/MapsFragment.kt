package com.example.gasapp

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val start = LatLng(52.32, 16.82)
        val gas_station1 = LatLng(52.331, 16.804)
        val gas_station2 = LatLng(52.315, 16.880)
        val gas_station3 = LatLng(52.349, 16.824)
        val gas_station4 = LatLng(52.354, 16.840)
        val gas_station5 = LatLng(52.339, 16.874)
        googleMap.addMarker(MarkerOptions().position(gas_station1).title("Mat-Oil"))
        googleMap.addMarker(MarkerOptions().position(gas_station2).title("BP Łęczyca"))
        googleMap.addMarker(MarkerOptions().position(gas_station3).title("Mikstol"))
        googleMap.addMarker(MarkerOptions().position(gas_station4).title("Auchan"))
        googleMap.addMarker(MarkerOptions().position(gas_station5).title("Orlen Luboń"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
package com.example.android.mytaxi.view.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.cericatto.mytaxitest.domain.retrofit.MyTaxiServiceRetrofit
import br.cericatto.mytaxitest.domain.utils.NavigationUtils
import br.cericatto.mytaxitest.model.VehicleData
import com.example.android.mytaxi.AppConfiguration
import com.example.android.mytaxi.R
import com.example.android.mytaxi.view.dialog.DialogUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class Task2Activity: AppCompatActivity(), OnMapReadyCallback {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val INITIAL_ZOOM = 10f
    }

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    // Context.
    private val mActivity = this@Task2Activity

    // Map.
    private var mMap: GoogleMap? = null

    private lateinit var mProgress: ProgressDialog

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)

        mProgress = DialogUtils.showProgressDialog(mActivity, getString(R.string.dialog_loading))
        initGoogleMaps()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        NavigationUtils.animate(mActivity, NavigationUtils.Animation.BACK)
    }

    //--------------------------------------------------
    // Menu Methods
    //--------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Change the map type based on the user's selection.
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.normal_map -> {
                mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            R.id.hybrid_map -> {
                mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID
                return true
            }
            R.id.satellite_map -> {
                mMap!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.terrain_map -> {
                mMap!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //--------------------------------------------------
    // Maps and Permissions
    //--------------------------------------------------

    /**
     * Triggered when the map is ready to be used.
     *
     * @param googleMap The GoogleMap object representing the Google Map.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Map configurations.
        setMapLongClick(mMap!!) // Set a long click listener for the map;
        setPoiClick(mMap!!) // Set a click listener for points of interest.
        setMapStyle(mMap!!) // Set the custom map style.
        enableMyLocation(mMap) // Enable location tracking.

        getData()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        // Check if location permissions are granted and if so enable the location data layer.
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation(mMap)
                }
        }
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private fun initGoogleMaps() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Adds a blue marker to the map when the user long clicks on it.
     * @param map The GoogleMap to attach the listener to.
     */
    private fun setMapLongClick(map: GoogleMap) {
        // Add a blue marker to the map when the user performs a long click.
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                getString(R.string.lat_long_snippet),
                latLng.latitude,
                latLng.longitude
            )

            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }

    /**
     * Adds a marker when a place of interest (POI) is clicked with the name of
     * the POI and immediately shows the info window.
     *
     * @param map The GoogleMap to attach the listener to.
     */
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
            poiMarker.tag = getString(R.string.poi)
        }
    }

    /**
     * Loads a style from the map_style.json file to style the Google Map. Log
     * the errors if the loading fails.
     *
     * @param map The GoogleMap object to style.
     */
    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(AppConfiguration.TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(AppConfiguration.TAG, "Can't find style. Error: ", e)
        }
    }

    /**
     * Checks for location permissions, and requests them if they are missing.
     * Otherwise, enables the location layer.
     */
    private fun enableMyLocation(map: GoogleMap?) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map!!.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun getData() {
        MyTaxiServiceRetrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                var list = it.poiList
                setMarkersOnMap(list)
            }
    }

    private fun setMarkersOnMap(list: List<VehicleData>) {
        val center = getLatLng(list[0])
        // Pan the camera to your home address (in this case, Google HQ).
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(center, INITIAL_ZOOM))

        // Get all markers.
        for (data in list) {
            val latLng = getLatLng(data)

            val snippet = String.format(
                Locale.getDefault(),
                getString(R.string.lat_long_snippet),
                latLng.latitude,
                latLng.longitude
            )
            mMap?.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
        mProgress.dismiss()
    }

    private fun getLatLng(data: VehicleData): LatLng {
        val lat = data.coordinate.latitude
        val lon = data.coordinate.longitude
        return LatLng(lat.toDouble(), lon.toDouble())
    }
}
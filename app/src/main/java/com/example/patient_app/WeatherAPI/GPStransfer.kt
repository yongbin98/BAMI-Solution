package com.example.patient_app.WeatherAPI

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log

@SuppressLint("MissingPermission")
class GPStransfer(
    activity: Activity,
    private val loc: (String) -> Boolean,
) {
    private val locationManager: LocationManager by lazy(LazyThreadSafetyMode.NONE) {
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private lateinit var myLocationListener: MyLocationListener
    private val TAG = "GPStransfer"

    fun getmylocation(){
        Log.i("GPS","check GPS Permission")
        val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(isGpsEnable){
            Log.i("GPS","call Loc listener")
            setLocationListener()
        }
    }

    fun setLocationListener() {
        val minTime: Long = 1500
        val minDistance = 100f

        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener() { latitude, longitude ->
                loc("$latitude,$longitude")
                true
            }
            Log.i("GPS","loc listener is initialized")
        }

        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )

            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, myLocationListener
            )
        }
    }

    inner class MyLocationListener(
        var loc: (Double,Double) -> Boolean
    ) : LocationListener{

        override fun onLocationChanged(location: Location) {
            Log.i(TAG,"${location.latitude}, ${location.longitude}")
            Log.i("GPS","loc listener called")
            loc(location.latitude,location.longitude)
            removeLocationListener()
        }

        fun removeLocationListener(){
            if(::myLocationListener.isInitialized){
                locationManager.removeUpdates(myLocationListener)
            }
        }

    }

}
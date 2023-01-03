package com.example.patient_app.WeatherAPI

import android.app.Activity
import android.location.Geocoder
import android.util.Log
import com.example.patient_app.bluetooth.BleService
import kotlinx.coroutines.*
import java.util.Locale

object APIService {
    private val myCoroutinescope = CoroutineScope(Dispatchers.IO)
    private lateinit var gpst : GPStransfer
    private lateinit var weatherapi : WeatherAPI

    fun connect(
        activity:Activity,
        weather_data : (StringBuilder) -> Boolean
    ) {
        Log.i("API","call GPS transfer")
        gpst = GPStransfer(activity){ loc ->
            Log.i("API","call Weather API")
            val loc_x = loc.substring(0,loc.indexOf(',')).toDouble()
            val loc_y = loc.substring(loc.indexOf(',')+1,).toDouble()
            weatherapi = WeatherAPI(loc_x,loc_y) { it ->
                Log.i("API","Coroutinescope launch")
                val geoData = Geocoder(activity, Locale.KOREA).getFromLocation(loc_x,loc_y,1)
                    ?.first()
                    ?.getAddressLine(0)
                weather_data(it.append("$geoData"))
                true
            }
            weatherapi.convertGRIDDtoGPS()
            weatherapi.setInit()
            myCoroutinescope.launch {
                val job = myCoroutinescope.async {
                    weatherapi.runAPI()
                    Log.i("API","Coroutinescope stopped")
                }
                job.await()
            }
            true
        }
        gpst.getmylocation()
    }

}

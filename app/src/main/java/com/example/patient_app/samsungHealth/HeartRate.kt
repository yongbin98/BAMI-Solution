package com.example.patient_app.samsungHealth

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Handler
import android.util.Log
import com.samsung.android.sdk.healthdata.*
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadRequest
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadResult
import com.samsung.android.sdk.healthdata.HealthResultHolder.ResultListener
import java.util.*


class HeartRate {
    private var mStore: HealthDataStore
    private var mHeartRateObserver: HeartRateObserver
    private val mHealthDataObserver: HealthDataObserver
    private val mHealthDataResolver: HealthDataResolver
    private val TAG = "HeartRate"

    constructor(
        store: HealthDataStore, listener: HeartRateObserver,
        resultHandler: Handler?
    ) {
        mStore = store
        mHeartRateObserver = listener
        mHealthDataResolver = HealthDataResolver(mStore, resultHandler)
        mHealthDataObserver = object : HealthDataObserver(resultHandler) {
            // Update the step count when a change event is received
            override fun onChange(dataTypeName: String) {
                Log.d(TAG, "Observer receives a HR data changed event")
                readHeartRateData()
            }
        }
    }
    // Use the SDK to connect to the Galaxy Watch and get heart rate data
    fun start() {
        HealthDataObserver.addObserver(
            mStore,
            HealthConstants.HeartRate.HEALTH_DATA_TYPE,
            mHealthDataObserver
        )
        readHeartRateData()
    }

    fun stop() {
        HealthDataObserver.removeObserver(mStore, mHealthDataObserver)
        mStore!!.disconnectService()
    }

    // Use a listener to receive the heart rate data and display it on the phone
    private val mHeartRateObserverListener: HealthDataObserver = object : HealthDataObserver(null) {
        override fun onChange(dataTypeName: String) {
            readHeartRateData()
        }
    }

    private fun readHeartRateData() {
        val resolver = HealthDataResolver(mStore, null)

        val request = ReadRequest.Builder()
            .setDataType(HealthConstants.HeartRate.HEALTH_DATA_TYPE)
            .setProperties(arrayOf(HealthConstants.HeartRate.HEART_RATE))
            .build()
        try {
            resolver.read(request)
                .setResultListener{ readResult : ReadResult ->
                    readResult.use { result ->
                        val iterator: Iterator<HealthData> = result.iterator()
                        if (iterator.hasNext()) {
                            mHeartRateObserver!!.onChanged(iterator.next().getFloat(HealthConstants.HeartRate.HEART_RATE))
                        }
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    interface HeartRateObserver {
        fun onChanged(count: Float)
    }
}
package com.example.patient_app.samsungHealth

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Handler
import android.util.Log
import com.samsung.android.sdk.healthdata.HealthConstants
import com.samsung.android.sdk.healthdata.HealthDataObserver
import com.samsung.android.sdk.healthdata.HealthDataResolver
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadRequest
import com.samsung.android.sdk.healthdata.HealthDataStore
import com.samsung.android.sdk.healthdata.HealthResultHolder.ResultListener


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
            mHeartRateObserver!!.onChanged(resolver.read(request).setResultListener(mHeartRateResultListener))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("Range")
    private val mHeartRateResultListener =
        ResultListener<HealthDataResolver.ReadResult> { result ->
            try {
                val c: Cursor? = result.resultCursor
                if (c != null) {
                    while (c.moveToNext()) {
                        val heartRate: Int =
                            c.getInt(c.getColumnIndex(HealthConstants.HeartRate.HEART_RATE))
                        // Display the heart rate on the phone
                        // ...
                    }
                }
            } finally {
                result.close()
            }
        }

    interface HeartRateObserver {
        fun onChanged(count: Unit)
    }
}
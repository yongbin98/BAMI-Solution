package com.example.patient_app.samsungHealth

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.samsung.android.sdk.healthdata.*
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadRequest
import com.samsung.android.sdk.healthdata.HealthDataResolver.ReadResult
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


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

    private fun readHeartRateData() {
        val endTime = getUtcStartOfDay(System.currentTimeMillis(), TimeZone.getDefault())
        val startTime = endTime - TimeUnit.HOURS.toMillis(1)
        val resolver = HealthDataResolver(mStore, null)

        val request = ReadRequest.Builder()
            .setDataType(HealthConstants.HeartRate.HEALTH_DATA_TYPE)
            .setLocalTimeRange(
                HealthConstants.HeartRate.START_TIME,
                HealthConstants.HeartRate.TIME_OFFSET,
                startTime,
                endTime)
            .build()
        try {
            resolver.read(request)
                .setResultListener{ readResult : ReadResult ->
                    readResult.use { result ->
                        val iterator: Iterator<HealthData> = result.iterator()
                        while(true) {
                            if (iterator.hasNext()) {
                                val tmpIterator = iterator.next()
                                if(tmpIterator.getBlob(HealthConstants.HeartRate.BINNING_DATA) != null) {
                                    mHeartRateObserver!!.onChanged(
                                        tmpIterator.getBlob(HealthConstants.HeartRate.BINNING_DATA)
                                    )
                                }
                            }
                            else
                                break
                        }
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun read(){
        readHeartRateData()
    }

    private fun getUtcStartOfDay(time: Long, tz: TimeZone): Long {
        val cal = Calendar.getInstance(tz)
        cal.timeInMillis = time
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val date = cal[Calendar.DATE]
        val hourofday = cal[Calendar.HOUR_OF_DAY]
        val minute = cal[Calendar.MINUTE]
        val second = cal[Calendar.SECOND]
        cal.timeZone = TimeZone.getTimeZone("UTC")
        cal[Calendar.YEAR] = year
        cal[Calendar.MONTH] = month
        cal[Calendar.DATE] = date
        cal[Calendar.HOUR_OF_DAY] = hourofday
        cal[Calendar.MINUTE] = minute
        cal[Calendar.SECOND] = second
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }


    interface HeartRateObserver {
        fun onChanged(count : ByteArray)
    }
}
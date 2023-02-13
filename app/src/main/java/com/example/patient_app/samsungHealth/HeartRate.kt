package com.example.patient_app.samsungHealth

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.patient_app.SFTP.File
import com.example.patient_app.SFTP.FileType
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
                readHeartRateData(false)
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
        readHeartRateData(false)
    }

    fun stop() {
        HealthDataObserver.removeObserver(mStore, mHealthDataObserver)
        mStore!!.disconnectService()
    }

    fun readHeartRateData(isSaved : Boolean) {
        lateinit var request : ReadRequest

        if(isSaved){
            val endTime = getUtcStartOfDay(System.currentTimeMillis(), TimeZone.getDefault()) + TimeUnit.DAYS.toMillis(1)
            val startTime = endTime - TimeUnit.DAYS.toMillis(2)

            request = ReadRequest.Builder()
                .setDataType(HealthConstants.HeartRate.HEALTH_DATA_TYPE)
                .setLocalTimeRange(
                    HealthConstants.HeartRate.START_TIME,
                    HealthConstants.HeartRate.TIME_OFFSET,
                    startTime,
                    endTime)
                .build()
        }
        else{
            val startTime = getUtcStartOfDay(System.currentTimeMillis(), TimeZone.getDefault())
            val endTime = startTime + TimeUnit.DAYS.toMillis(1)

            request = ReadRequest.Builder()
                .setDataType(HealthConstants.HeartRate.HEALTH_DATA_TYPE)
                .setLocalTimeRange(
                    HealthConstants.HeartRate.START_TIME,
                    HealthConstants.HeartRate.TIME_OFFSET,
                    startTime,
                    endTime)
                .build()
        }

        try {
            mHealthDataResolver!!.read(request)
                .setResultListener{ readResult : ReadResult ->
                    readResult.use { result ->
                        val iterator: Iterator<HealthData> = result.iterator()
                        var file = File(FileType.startCharOf('H'))
                        file.write("heart_rate,heart_rate_min,heart_rate_max,start_time,end_time\n")
                        while(isSaved) {
                            if (iterator.hasNext()) {
                                val tmpIterator = iterator.next()
                                if(tmpIterator.getBlob(HealthConstants.HeartRate.BINNING_DATA) != null){
                                    mHeartRateObserver!!.onChanged(tmpIterator.getBlob(HealthConstants.HeartRate.BINNING_DATA), file)
                                }
                            }
                            else {
                                file.close()
                                break
                            }
                        }
                        if(!isSaved)
                            mHeartRateObserver!!.onChanged(result.last().getFloat(HealthConstants.HeartRate.HEART_RATE))
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getUtcStartOfDay(time: Long, tz: TimeZone): Long {
        val cal = Calendar.getInstance(tz)
        cal.timeInMillis = time
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val date = cal[Calendar.DATE]
        val hourofday = cal[Calendar.HOUR_OF_DAY]
        cal.timeZone = TimeZone.getTimeZone("UTC")
        cal[Calendar.YEAR] = year
        cal[Calendar.MONTH] = month
        cal[Calendar.DATE] = date
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }


    interface HeartRateObserver {
        fun onChanged(count : Float)
        fun onChanged(count : ByteArray, file : File)
    }
}
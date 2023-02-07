package com.example.patient_app.samsungHealth

import android.os.Handler
import android.util.Log
import com.samsung.android.sdk.healthdata.*
import com.samsung.android.sdk.healthdata.HealthDataResolver.AggregateRequest
import com.samsung.android.sdk.healthdata.HealthDataResolver.AggregateRequest.AggregateFunction
import com.samsung.android.sdk.healthdata.HealthDataResolver.AggregateResult
import java.util.*
import java.util.concurrent.TimeUnit

class StepCount {
    private val TAG = "StepCount"
    private var mStore: HealthDataStore
    private var mStepCountObserver: StepCountObserver
    private var mHealthDataResolver: HealthDataResolver
    private var mHealthDataObserver: HealthDataObserver

    constructor(
        store: HealthDataStore, listener: StepCountObserver,
        resultHandler: Handler?
    ) {
        mStore = store
        mStepCountObserver = listener
        mHealthDataResolver = HealthDataResolver(mStore, resultHandler)
        mHealthDataObserver = object : HealthDataObserver(resultHandler) {
            // Update the step count when a change event is received
            override fun onChange(dataTypeName: String) {
                Log.d(TAG, "Observer receives a step data changed event")
                readTodayStepCount()
            }
        }
    }


    fun start() {
        // Register an observer to listen changes of step count and get today step count
        HealthDataObserver.addObserver(
            mStore,
            HealthConstants.StepCount.HEALTH_DATA_TYPE,
            mHealthDataObserver
        )
        readTodayStepCount()
    }

    fun read(){
        readTodayStepCount()
    }

    fun stop() {
        HealthDataObserver.removeObserver(mStore, mHealthDataObserver)
        mStore!!.disconnectService()
    }

    // Read the today's step count on demand
    private fun readTodayStepCount() {
        // Set time range from start time of today to the current time
        val startTime = getUtcStartOfDay(System.currentTimeMillis(), TimeZone.getDefault())
        val endTime = startTime + TimeUnit.DAYS.toMillis(1)

        val request = AggregateRequest.Builder()
            .setDataType(HealthConstants.StepCount.HEALTH_DATA_TYPE)
            .addFunction(AggregateFunction.SUM, HealthConstants.StepCount.COUNT, "total_step")
            .setLocalTimeRange(
                HealthConstants.StepCount.START_TIME,
                HealthConstants.StepCount.TIME_OFFSET,
                startTime,
                endTime
            )
            .build()
        try {
            mHealthDataResolver!!.aggregate(request)
                .setResultListener { aggregateResult: AggregateResult ->
                    aggregateResult.use { result ->
                        val iterator: Iterator<HealthData> = result.iterator()
                        if (iterator.hasNext()) {
                            mStepCountObserver!!.onChanged(iterator.next().getInt("total_step"))
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Getting step count fails.", e)
        }
    }

    private fun getUtcStartOfDay(time: Long, tz: TimeZone): Long {
        val cal = Calendar.getInstance(tz)
        cal.timeInMillis = time
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val date = cal[Calendar.DATE]
        val hourofday = cal[Calendar.HOUR_OF_DAY]
        val minute = cal[Calendar.MINUTE]
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

    interface StepCountObserver {
        fun onChanged(count: Int)
    }
}
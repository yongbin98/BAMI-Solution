package com.example.patient_app.samsungHealth

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.patient_app.bluetooth.BleService
import com.samsung.android.sdk.healthdata.*
import com.samsung.android.sdk.healthdata.HealthDataStore.ConnectionListener
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int
import kotlin.Boolean
import kotlin.String


object HealthService {
    private val TAG = "HealthService"
    private lateinit var mStore: HealthDataStore
    private lateinit var mReportStep : StepCount
    private lateinit var mReportHR : HeartRate
    private lateinit var mConnectionListener: ConnectionListener

    fun connect(activity : Activity,
                check : (Boolean) -> Boolean){
        mConnectionListener = object :
            ConnectionListener {
            override fun onConnected() {
                val permKey_step = HealthPermissionManager.PermissionKey(
                    HealthConstants.StepCount.HEALTH_DATA_TYPE,
                    HealthPermissionManager.PermissionType.READ
                )
                val permKey_hr = HealthPermissionManager.PermissionKey(
                    HealthConstants.HeartRate.HEALTH_DATA_TYPE,
                    HealthPermissionManager.PermissionType.READ
                )
                Log.d(TAG, "Health data service is connected.")
                val pmsManager = HealthPermissionManager(mStore)
                if (!pmsManager.isPermissionAcquired(setOf(permKey_hr,permKey_step)).containsValue(false)) {
                    Log.d(TAG, "Permission accessed")
                    mReportStep = StepCount(mStore, mStepCountObserver, Handler(Looper.getMainLooper()))
                    mReportHR = HeartRate(mStore, mHeartRateObserver, Handler(Looper.getMainLooper()))
                    mReportStep.start()
                    mReportHR.start()
                    check(true)
                } else {
                    Log.d(TAG, "Permission callback is received.")
                    pmsManager.requestPermissions(setOf(permKey_hr,permKey_step), activity)
                }
            }

            override fun onConnectionFailed(error: HealthConnectionErrorResult) {
                Log.d(TAG, "Health data service is not available.")
                Log.i(TAG,"connection Failed : $error")
            }

            override fun onDisconnected() {
                Log.d(TAG, "Health data service is disconnected.")
                if (!activity.isFinishing) {
                    mStore!!.connectService()
                }
                disconnect()
            }
        }
    }

    fun start(activity: Activity){
        mStore = HealthDataStore(activity, mConnectionListener)
        mStore.connectService()
    }

    fun disconnect(){
        mReportStep.stop()
        mReportHR.stop()
        mStore!!.disconnectService()
    }

    private val mStepCountObserver = object : StepCount.StepCountObserver{
        var stepcount : Int = 0
        var isChanged : Boolean = false
        override fun onChanged(count: Int) {
            Log.i(TAG,"Step : $count")
            stepcount = count
            isChanged = true
        }
    }

    private val mHeartRateObserver = object : HeartRate.HeartRateObserver{
        var heartRate : Float = 0f
        var isChanged : Boolean = false
        override fun onChanged(count: ByteArray){
            var zip = getLiveData(count)
            var tmpiter = zip.iterator()
            var i = 0
            var dateFormat = SimpleDateFormat("HH:mm:ss")

            while(tmpiter.hasNext()){
                var tmpNext = tmpiter.next()
                i++
            }
            isChanged = true
            heartRate = zip.last().heart_rate
        }
    }

    class LiveData {
        var heart_rate = 0F
        var heart_rate_min = 0F
        var heart_rate_max = 0F
        var start_time = 0L
        var end_time = 0L
    }

    fun getLiveData(zip: ByteArray?): List<LiveData> {
        // decompress ZIP
        return HealthDataUtil.getStructuredDataList(
            zip,
            LiveData::class.java
        )
    }

    fun UpdateHealthData(){
        mReportStep.read()
        mReportHR.read()
    }

    suspend fun getHealthData() : Pair<Float, Int>{
        while(!(mHeartRateObserver.isChanged && mStepCountObserver.isChanged))
            delay(1000)

        return Pair(mHeartRateObserver.heartRate, mStepCountObserver.stepcount)

    }
}
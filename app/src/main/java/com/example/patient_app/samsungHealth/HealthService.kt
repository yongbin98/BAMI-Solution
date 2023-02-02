package com.example.patient_app.samsungHealth

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult
import com.samsung.android.sdk.healthdata.HealthConstants
import com.samsung.android.sdk.healthdata.HealthDataStore
import com.samsung.android.sdk.healthdata.HealthDataStore.ConnectionListener
import com.samsung.android.sdk.healthdata.HealthPermissionManager
import java.lang.Boolean
import kotlin.Int

object HealthService {
    private val TAG = "HealthService"
    private lateinit var mStore: HealthDataStore
    private lateinit var mReportStep : StepCount
    private lateinit var mReportHR : HeartRate
    private val permKey_step = HealthPermissionManager.PermissionKey(
        HealthConstants.StepCount.HEALTH_DATA_TYPE,
        HealthPermissionManager.PermissionType.READ
    )
    private val permKey_hr = HealthPermissionManager.PermissionKey(
        HealthConstants.HeartRate.HEALTH_DATA_TYPE,
        HealthPermissionManager.PermissionType.READ
    )
    private lateinit var mConnectionListener: ConnectionListener

    fun connect(activity : Activity,
    check : (kotlin.Boolean) -> kotlin.Boolean){
        mConnectionListener = object :
            ConnectionListener {
            override fun onConnected() {
                Log.d(TAG, "Health data service is connected.")
                val pmsManager = HealthPermissionManager(mStore)
                if (!pmsManager.isPermissionAcquired(setOf(permKey_hr,permKey_step)).containsValue(Boolean.FALSE)) {
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
        override fun onChanged(count: Int) {
            Log.i(TAG,"Step : $count")
        }
    }

    private val mHeartRateObserver = object : HeartRate.HeartRateObserver{
        override fun onChanged(count: Float) {
            Log.i(TAG,"Heartrate : $count")


        }
    }
}
package com.example.patient_app.samsungHealth

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.patient_app.SFTP.File
import com.example.patient_app.SFTP.FileType
import com.samsung.android.sdk.healthdata.*
import com.samsung.android.sdk.healthdata.HealthDataStore.ConnectionListener
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
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
            Log.i(TAG, "Step Int Called")
            isChanged = true
        }

        override fun onChanged(count: StringBuilder) {
            var file = File(FileType.startCharOf('C'))
            file.write("start_time(1min),step,kilocalories,distance(m),avg_speed(m/s)\n")
            Log.i(TAG, "Step ByteArray Called")
            file.write(count.toString())
            file.close()
            isChanged = true
        }
    }

    private val mHeartRateObserver = object : HeartRate.HeartRateObserver{
        var heartRate : Float = 0f
        var isChanged : Boolean = false

        override fun onChanged(count: Boolean) {
            isChanged = true
        }

        override fun onChanged(count: ByteArray, file : File){
            var zip = HealthDataUtil.getStructuredDataList(count, HeartLiveData::class.java)
            var tmpiter = zip.iterator()
            var dateFormat = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
            Log.i(TAG, "HR ByteArray Called")

            while(tmpiter.hasNext()){
                var tmpNext = tmpiter.next()
                file.write(tmpNext.heart_rate.toInt().toString()+',')
                file.write(tmpNext.heart_rate_min.toInt().toString()+',')
                file.write(tmpNext.heart_rate_max.toInt().toString()+',')
                file.write(dateFormat.format(tmpNext.start_time).toString()+',')
                file.write(dateFormat.format(tmpNext.end_time).toString()+"\n")
                Log.i(TAG,"${dateFormat.format(tmpNext.start_time).toString()}!!")
            }
            isChanged = true

        }

        override fun onChanged(count: Float) {
            Log.i(TAG, "HR Float Called")
            heartRate = count
            isChanged = true
        }
    }

    class HeartLiveData {
        var heart_rate = 0F
        var heart_rate_min = 0F
        var heart_rate_max = 0F
        var start_time = 0L
        var end_time = 0L
    }

    fun updateHealthData(isSaved : Boolean){
        mHeartRateObserver.isChanged = false
        mStepCountObserver.isChanged = false
        mReportHR.readHeartRateData(isSaved)
        mReportStep.readTodayStepCount(isSaved)
    }

    suspend fun getHealthData() : Pair<Float, Int>{
        while(!(mHeartRateObserver.isChanged && mStepCountObserver.isChanged))
            delay(1000)

        return Pair(mHeartRateObserver.heartRate, mStepCountObserver.stepcount)
    }
}
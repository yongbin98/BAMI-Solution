package com.example.patient_app.Activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.patient_app.WeatherAPI.WeatherAPI
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isInvisible
import com.example.patient_app.R
import com.example.patient_app.WeatherAPI.APIService
import com.example.patient_app.samsungHealth.HealthService
import com.example.patient_app.samsungHealth.HeartRate
import com.example.patient_app.samsungHealth.PERMISSIONS
import com.example.patient_app.samsungHealth.REQUEST_ALL_PERMISSION
import com.google.android.material.snackbar.Snackbar
import com.samsung.android.sdk.healthdata.HealthConstants.StepCount
import com.samsung.android.sdk.healthdata.HealthDataStore
import com.samsung.android.sdk.healthdata.HealthPermissionManager
import com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionKey
import com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.days
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {
    private val TAG = "Main"
    private val myCoroutinescope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val utilDate = Date()
        val formatType = SimpleDateFormat("yyyy년 MM월 dd일")
        date.text = formatType.format(utilDate)
        var cutLocation: String


        btn_SamsungHealth.setOnClickListener {
            myCoroutinescope.launch {
                HealthService.connect(this@MainActivity) {
                    Log.i(TAG, "Get Permission!!")
                    true
                }
                Log.i(TAG, "START")
                HealthService.start(this@MainActivity)
                val (HR, StepCheck) = HealthService.getHealthData()
                Log.i(TAG, "HR : $HR , Stepcount : $StepCheck")
                APIService.connect(this@MainActivity) {
                    myCoroutinescope.launch {
                        weatherScreen(it)
                        rate.text = HR.toInt().toString()
                        step.text = StepCheck.toString()
                        MainActivity_HR.HR = HR.toInt().toString()
                        MainActivity_HR.Steps = StepCheck.toInt().toString()
                        btn_SamsungHealth.visibility = Button.GONE
                        reload_btn.visibility = Button.VISIBLE
                    }
                    true
                }
            }

            btn_Survey.setOnClickListener {
                MainActivity_HR.treatYear = MainActivity_HR.Patient_ID[0].toString()
                var startday = MainActivity_HR.Patient_ID.substring(8)
                val cal = Calendar.getInstance()
                cal.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
                cal[Calendar.MONTH] = startday.substring(0, 2).toInt() - 1
                cal[Calendar.DATE] = startday.substring(2, 4).toInt()
                MainActivity_HR.timeDiff =
                    (System.currentTimeMillis() - cal.timeInMillis) / (1000 * 60 * 60 * 24)
                Log.i(TAG, "${MainActivity_HR.timeDiff.rem(7) == 0L}")
                if (MainActivity_HR.treatYear == "2" || MainActivity_HR.treatYear == "3") {
                    val intent = Intent(this, VAS_2and3yearActivity::class.java)
                    startActivity((intent))
                } else
                    Toast.makeText(this, "ID 오류가 발생하였습니다.", Toast.LENGTH_SHORT)
            }


            reload_btn.setOnClickListener {

                myCoroutinescope.launch {
                    rate.text = "-"
                    step.text = "-"
                    HealthService.updateHealthData(false)
                    val (HR, StepCheck) = HealthService.getHealthData()
                    Log.i(TAG, "HR : $HR , Stepcount : $StepCheck")
                    APIService.connect(this@MainActivity) {
                        myCoroutinescope.launch {
                            weatherScreen(it)
                            rate.text = HR.toInt().toString()
                            step.text = StepCheck.toString()
                            MainActivity_HR.HR = HR.toInt().toString()
                            MainActivity_HR.Steps = StepCheck.toInt().toString()
                        }
                        true
                    }
                }
            }



            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.menu)

            if (MainActivity_HR.HR != "") {
                rate.text = MainActivity_HR.HR
                btn_SamsungHealth.visibility = Button.GONE
                reload_btn.visibility = Button.VISIBLE

            }
            if (MainActivity_HR.Steps != "")
                step.text = MainActivity_HR.Steps

            requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)

        }
    }


    private var backPressedTime : Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime < 2000){
            ActivityCompat.finishAffinity(this)
            System.exit(0)
            return
        }

        Toast.makeText(this,"'뒤로' 버튼을 한 번 더 누르시면 앱이 종료됩니다.",Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.App_version -> {
                Snackbar.make(toolbar, "버전 정보 : 0.0.0", Snackbar.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun weatherScreen(it : StringBuilder){
        it.split('\n').let {
            it.forEachIndexed { index, text ->
                if (index == 0) {
                    if (text.substring(7) == "0") {
                        val sunImg = getDrawable(R.drawable.sun_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            sunImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "1") {
                        val rainyImg = getDrawable(R.drawable.rainy_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            rainyImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "2") {
                        val rainsnowImg = getDrawable(R.drawable.rainsnow_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            rainsnowImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "3") {
                        val snowImg = getDrawable(R.drawable.snow_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            snowImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "4") {
                        val rainyImg = getDrawable(R.drawable.rainy_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            rainyImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "5") {
                        val cloudyImg = getDrawable(R.drawable.cloudy_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            cloudyImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "6") {
                        val lessrainsnowImg = getDrawable(R.drawable.lessrainsnow_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            lessrainsnowImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "7") {
                        val snowstormImg = getDrawable(R.drawable.snowstorm_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            snowstormImg,
                            null,
                            null,
                            null
                        )
                    }
                }
                if (index == 1)
                    weather.text = " " + text
                if (index == 2)
                    local.text = text.substring(4)
                Log.i(TAG, "$text")
            }
        }
    }

}









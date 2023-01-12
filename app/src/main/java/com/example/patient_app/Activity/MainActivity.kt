package com.example.patient_app.Activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Address
import android.net.wifi.ScanResult
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.patient_app.R
import com.example.patient_app.WeatherAPI.APIService
import com.example.patient_app.bluetooth.BleService
import com.example.patient_app.bluetooth.PERMISSIONS
import com.example.patient_app.bluetooth.REQUEST_ALL_PERMISSION
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity(){
    private val TAG = "Main"
    private val myCoroutinescope = CoroutineScope(Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)

//        var deviceArray : Array<String> = emptyArray()
//        var deviceAddress : Array<String> = emptyArray()
//
//        BleService.scanDevices(this@MainActivity)
//        BleService.scanResults.forEachIndexed{index, ScanResult ->
//            if (index ==0){
//                deviceArray=deviceArray.plus(ScanResult.device.name)
//                deviceAddress=deviceAddress.plus(ScanResult.device.address)}
//            else {deviceArray = deviceArray.plus(ScanResult.device.name)
//                deviceAddress=deviceAddress.plus(ScanResult.device.address)}
//        }
//        deviceArray = deviceArray.distinct().toTypedArray()
//        deviceAddress = deviceAddress.distinct().toTypedArray()
//
//
//
//
//        val negativeButtonClick = {dialogInterface: DialogInterface, i:Int ->
//            Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
//        }
//
//        val ReloadButtonClick = {dialogInterface: DialogInterface, i :Int ->
//            BleService.scanResults.forEachIndexed{index, ScanResult ->
//                if (index ==0){
//                    deviceArray=deviceArray.plus(ScanResult.device.name)
//                    deviceAddress=deviceAddress.plus(ScanResult.device.address)}
//                else {deviceArray = deviceArray.plus(ScanResult.device.name)
//                    deviceAddress=deviceAddress.plus(ScanResult.device.address)}
//            }
//            deviceArray = deviceArray.distinct().toTypedArray()
//            deviceAddress = deviceAddress.distinct().toTypedArray()
//        }
//
//
//        btn_SamsungHealth.setOnClickListener {
//            val ListBuilder = AlertDialog.Builder(this)
//                .setTitle("기기목록")
//                .setPositiveButtonIcon(getDrawable(R.drawable.reload_icon))
//                .setPositiveButton("", ReloadButtonClick).setCancelable(false)
//                .setItems(deviceArray) { dialog, which ->
//                    BleService.scanResults.forEach {
//                        if (it.device.address.contains(deviceAddress[which])) {
//                            BleService.connect(this@MainActivity, it.device)
//                            myCoroutinescope.launch {
//                                BleService.isbleUpdated()
//                                Toast.makeText(this@MainActivity, "연결 완료", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//
//                }.show()
//        }

        val packageName : String = "com.sec.android.app.shealth"

        fun isAppInstalled(packageName: String, packageManager: PackageManager) : Boolean{
            return try{
                packageManager.getPackageInfo(packageName,0)
                true
            }catch (ex: PackageManager.NameNotFoundException){
                false
            }
        }

        val packageManager: PackageManager = packageManager

        btn_SamsungHealth.setOnClickListener{
            if(isAppInstalled(packageName,packageManager)){
                val InstallBuilder = AlertDialog.Builder(this)
                    .setTitle(R.string.btn_samsungHealth)
                    .setIcon(R.drawable.samsunghealth_icon)
                    .setMessage(R.string.SH_info)
                    .setPositiveButton(R.string.btn_samsungHealth, DialogInterface.OnClickListener{dialog, which ->
                        Toast.makeText(application,"연결",Toast.LENGTH_SHORT).show()

                    }).show()

            }
            else{
                val Builder = AlertDialog.Builder(this)
                    .setTitle(R.string.btn_samsungHealth)
                    .setIcon(R.drawable.samsunghealth_icon)
                    .setMessage(R.string.SH_info)
                    .setPositiveButton("Install Samsung Health", DialogInterface.OnClickListener{dialog, which ->
                        Toast.makeText(application,"설치",Toast.LENGTH_SHORT).show()
                    }).show()
            }
                }





        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.menu)




        val utilDate = Date()
        val formatType = SimpleDateFormat("yyyy년 MM월 dd일")
        date.text = formatType.format(utilDate)

        var cutLocation : String


        val sunImg = getDrawable(R.drawable.sun_icon)
        val rainyImg = getDrawable(R.drawable.rainy_icon)
        val rainsnowImg = getDrawable(R.drawable.rainsnow_icon)
        val snowImg = getDrawable(R.drawable.snow_icon)
        val cloudyImg = getDrawable(R.drawable.cloudy_icon)
        val lessrainsnowImg = getDrawable(R.drawable.lessrainsnow_icon)
        val snowstormImg = getDrawable(R.drawable.snowstorm_icon)




        APIService.connect(this){
            myCoroutinescope.launch {
                it.split('\n').let{
                    it.forEachIndexed { index, text ->
                        if(index == 0){
                            if(text.substring(7) == "0")
                                weather.setCompoundDrawablesWithIntrinsicBounds(sunImg,null,null,null)
                            else if (text.substring(7) == "1")
                                weather.setCompoundDrawablesWithIntrinsicBounds(rainyImg,null,null,null)
                            else if (text.substring(7) == "2")
                                weather.setCompoundDrawablesWithIntrinsicBounds(rainsnowImg,null,null,null)
                            else if (text.substring(7) == "3")
                                weather.setCompoundDrawablesWithIntrinsicBounds(snowImg,null,null,null)
                            else if (text.substring(7) == "4")
                                weather.setCompoundDrawablesWithIntrinsicBounds(rainyImg,null,null,null)
                            else if (text.substring(7) == "5")
                                weather.setCompoundDrawablesWithIntrinsicBounds(cloudyImg,null,null,null)
                            else if (text.substring(7) == "6")
                                weather.setCompoundDrawablesWithIntrinsicBounds(lessrainsnowImg,null,null,null)
                            else if (text.substring(7) == "7")
                                weather.setCompoundDrawablesWithIntrinsicBounds(snowstormImg,null,null,null)
                        }
                        if(index == 1)
                            weather.text = " "+text
                        if(index == 2)
                            local.text = text.substring(4)
                        Log.i(TAG,"$text")
                    }
                }
            }
            true
        }











        // weather.text = callWeather

        //local.text =



//        btn1.setOnClickListener {
//            Log.i(TAG, "btn1 On clicked")
//        }
//
//        btn2.setOnClickListener {
//            Log.i(TAG, "btn2 On clicked")
//
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.survey -> {
                val intent = Intent(this, Survey_1Activity::class.java)
                startActivity((intent))
                }
            R.id.App_version -> {
                Snackbar.make(toolbar,"버전 정보 : 0.0.0",Snackbar.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    }



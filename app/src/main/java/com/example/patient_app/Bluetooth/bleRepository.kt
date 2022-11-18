package com.example.patient_app.Bluetooth

import android.Manifest
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.patient_app.*
import com.example.patient_app.SFTP.File
import com.example.patient_app.SFTP.FileType
import java.util.*

class bleRepository : AppCompatActivity(){

    private val bleAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bleManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bleManager.adapter
    }
    private var scanResults: ArrayList<BluetoothDevice>? = ArrayList()
    private val TAG = "bleRepository"
    private var bleGatt: BluetoothGatt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainlayout)
        Log.i(TAG,"ble activity called")

        val backbtnOn = findViewById<Button>(R.id.backbtn)
        val testbtnOn = findViewById<Button>(R.id.testbtn)

        testbtnOn.setOnClickListener(){
            connectBLE()
        }

        backbtnOn.setOnClickListener(){
            Log.i(TAG,"finish btn clicked")
            disconnectBLE()
        }
    }

    companion object{
    }

    fun connectBLE() {
        //ble permission check
        while(true) {
            if (!hasPermissions(this, PERMISSIONS)) {
                requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
                Log.e(TAG, "Permission denied")
            }
            else {
                Log.i(TAG, "get Permission")
                break
            }
        }

        //ble adapter check request ble enable
        if (bleAdapter == null || !bleAdapter?.isEnabled!!) {
            requestEnableBLE()
            Log.e(TAG, "Scanning Failed : ble does not enabled")
            return
        }

        //check bluetooth name
        var filters: MutableList<ScanFilter> = ArrayList()
        Log.i(TAG,"scan device using MAC")
        Log.i(TAG,"find MAC : $SERVICE_MAC_ADDRESS")
        val scanFilter: ScanFilter = ScanFilter.Builder()
//                .setServiceUuid(ParcelUuid(UUID.fromString(SERVICE_STRING))) // UUID로 검색
//                .setDeviceName(editview.text.toString())
            .setDeviceAddress(SERVICE_MAC_ADDRESS)
            .build()
        filters.add(scanFilter)

        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG,"Permission Denied..")
            requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
        }
        else {
            bleAdapter?.bluetoothLeScanner?.startScan(filters, settings, BLEScanCallback)
//                bleAdapter?.bluetoothLeScanner?.startScan(BLEScanCallback) //filtering X
        }
    }

    fun disconnectBLE(){
        stopScan()
        disconnectGattServer()
    }

    /**
     * Request BLE enable
     */
    fun requestEnableBLE() {
        val bleEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.e(TAG,"requestEnableBLE Denied..")
            return
        }
        startActivityForResult(bleEnableIntent, REQUEST_ENABLE_BT)
    }

    /**
     * Permission check
     */
    private fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null){
            for (permission in permissions) {
                if(ActivityCompat.checkSelfPermission(context,permission)
                    != PackageManager.PERMISSION_GRANTED){
                    return false
                }
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ALL_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG,"Permissions granted!")
                } else {
                    requestPermissions(permissions, REQUEST_ALL_PERMISSION)
                    Log.e(TAG,"Permissions must be granted")
                }
            }
        }
    }


    /**
     * BLE Scan Callback
     */
    private val BLEScanCallback: ScanCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            if (ActivityCompat.checkSelfPermission(
                    this@bleRepository,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e(TAG,"Connect denied")
                return
            }
            Log.i(TAG, "Remote device name: " + result.device.name)
            addScanResult(result)
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            for (result in results) {
                addScanResult(result)
            }
        }

        override fun onScanFailed(_error: Int) {
            Log.e(TAG, "BLE scan failed with code $_error")
        }

        /**
         * Add scan result
         */
        private fun addScanResult(result: ScanResult) {
            // get scanned device
            val device = result.device
            // get scanned device MAC address
            val deviceAddress = device.address
            val deviceName = device.name

            // 중복 체크
            for (dev in scanResults!!) {
                if (dev.address == deviceAddress) return
            }
            // add arrayList
            scanResults?.add(result.device)
            // status text UI update
            Log.i(TAG,"add scanned device: $deviceAddress")

            if (ActivityCompat.checkSelfPermission(
                    this@bleRepository,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e(TAG,"Connect denied")
                return
            }
            stopScan()
            bleGatt = device?.connectGatt(this@bleRepository, true, gattClientCallback)
        }
    }

    /**
     * Scan stop
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun stopScan(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG,"stopscan Denied..")
            return
        }
        bleAdapter?.bluetoothLeScanner?.stopScan(BLEScanCallback)
        //isScanning.set(false) //스캔 중지(버튼 활성화)
        //btnScanTxt.set("Start Scan") //button text update
        scanResults = ArrayList() //list 초기화
        Log.d(TAG, "BLE Stop!")
    }
    /**
     * BLE gattClientCallback
     */
    private val gattClientCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if( status == BluetoothGatt.GATT_FAILURE ) {
                disconnectGattServer()
                return
            } else if( status != BluetoothGatt.GATT_SUCCESS ) {
                disconnectGattServer()
                return
            }
            if( newState == BluetoothProfile.STATE_CONNECTED ) {
                // update the connection status message

                Log.d(TAG, "Connected to the GATT server")
                if (ActivityCompat.checkSelfPermission(
                        this@bleRepository,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Log.e(TAG, "connect permission denied")
                    return
                }
                gatt.discoverServices()
            } else if ( newState == BluetoothProfile.STATE_DISCONNECTED ) {
                disconnectGattServer()
            }
        }
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)

            // check if the discovery failed
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Log.e(TAG, "Device service discovery failed, status: $status")
                return
            }

            // log for successful discovery
            Log.d(TAG, "Services discovery is successful")

            // find command characteristics from the GATT server
            val respCharacteristic = gatt?.let { BluetoothUtils.findResponseCharacteristic(it) }

            /**
             * BLE UUID 직접 찾기
             */
//            val services = gatt?.services
//            if(services != null) {
//                for (service in services) {
//                    val characteristics = service?.characteristics
//
//                    if(characteristics != null){
//                        for (characteristic in characteristics) {
//                            if(characteristic.properties == BluetoothGattCharacteristic.PROPERTY_WRITE or BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE )
//                                Log.d(TAG, "WRITE Response : ${characteristic.uuid}")
//                            else if(characteristic.properties == BluetoothGattCharacteristic.PROPERTY_NOTIFY)
//                                Log.d(TAG, "NOTIFY : ${characteristic.uuid}")
//                            else
//                                Log.e(TAG, "ERROR : ${characteristic.uuid}")
//                        }
//                    }
//                }
//            }

            // disconnect if the characteristic is not found
            if( respCharacteristic == null ) {
                Log.e(TAG, "Unable to find cmd characteristic")
                disconnectGattServer()
                return
            }
            else
                Log.d(TAG, "find cmd characteristic successfully")

            // sending Success

            if (ActivityCompat.checkSelfPermission(
                    this@bleRepository,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e(TAG,"connect permission denied")
                return
            }
            // UUID 알람 설정
            gatt.setCharacteristicNotification(respCharacteristic, true)
            Log.d(TAG,"UUID Alarm on")

            // UUID for notification
            val descriptor: BluetoothGattDescriptor = respCharacteristic.getDescriptor(
                UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG)
            )
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            Log.d(TAG,"${descriptor.value}")
            gatt.writeDescriptor(descriptor)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
//            Log.d(TAG, "characteristic changed: " + characteristic.uuid.toString())
            readCharacteristic(characteristic)
        }

        @Synchronized
        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Characteristic written successfully")
            } else {
                Log.e(TAG, "Characteristic write unsuccessful, status: $status")
                disconnectGattServer()
            }
        }

        @Synchronized
        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Characteristic read successfully")
                readCharacteristic(characteristic)
            } else {
                Log.e(TAG, "Characteristic read unsuccessful, status: $status")
                // Trying to read from the Time Characteristic? It doesnt have the property or permissions
                // set to allow this. Normally this would be an error and you would want to:
                // disconnectGattServer();
            }
        }

        /**
         * Log the value of the characteristic
         * @param characteristic
         */
        // Bluetooth 통신 읽기
        var file: File? = null

        @Synchronized
        private fun readCharacteristic(characteristic: BluetoothGattCharacteristic) {
            var msg = characteristic.getStringValue(0)

            while (true) {
                val index = msg.indexOf(FileType.FINISH_CHAR)

                if (index != -1) {
                    write(msg.substring(0, index))
                    close()

                    msg = msg.substring(index + 1)
                } else {
                    write(msg)
                    break
                }
            }
        }

        private fun write(msg: String) {
            try {
                var writeMsg = msg

                if (file == null) {
                    file = File(FileType.startCharOf(msg[0]))
                    writeMsg = msg.substring(1)
                    Log.i(TAG,"start data reading")
                    Log.i(TAG, "create file ${file!!.getFileName()}")
                }

                file!!.write(writeMsg)
            } catch (exception: RuntimeException) {
                exception.printStackTrace()
                file = null
            }
        }

        private fun close() {
            file!!.close()
            Log.i(TAG, "close file ${file!!.getFileName()}")
            Log.i(TAG,"file is created")
            file = null
        }
    }

    /**
     * Disconnect Gatt Server
     */
    fun disconnectGattServer() {
        Log.d(TAG, "Closing Gatt connection")
        // disconnect and close the gatt
        if (bleGatt != null) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e(TAG,"connect permission denied")
                return
            }
            bleGatt!!.disconnect()
            bleGatt!!.close()
        }
    }

    private fun write(write_txt : String){
        val cmdCharacteristic = BluetoothUtils.findCommandCharacteristic(bleGatt!!)
        // disconnect if the characteristic is not found
        if (cmdCharacteristic == null) {
            Log.e(TAG, "Unable to find cmd characteristic")
            disconnectGattServer()
            return
        }
        cmdCharacteristic.setValue(write_txt)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val success: Boolean = bleGatt!!.writeCharacteristic(cmdCharacteristic)

        // check the result
        if( !success ) {
            Log.e(TAG, "Failed to write command")
        }
    }
}
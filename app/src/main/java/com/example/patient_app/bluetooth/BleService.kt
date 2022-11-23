package com.example.patient_app.bluetooth

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import com.example.patient_app.SFTP.File
import com.example.patient_app.SFTP.FileType

@SuppressLint("MissingPermission")
object BleService {
    private val TAG = "BleService"
    private lateinit var bleGatt: BluetoothGatt
    private val files: MutableMap<Char, File> = mutableMapOf()

    private lateinit var bleRepository: BleRepository

    val isConnected: Boolean
        get() = ::bleGatt.isInitialized

    fun connect(activity: Activity) {
        bleRepository = BleRepository(activity) { device ->
            Log.i(TAG, "connected")
            bleGatt = device.connectGatt(activity, true, BluetoothCallback(this::readFromBluetooth))
            true
        }
        bleRepository.connectBLE()
    }

    fun disconnect() {
        Log.d(TAG, "Closing Gatt connection")

        if (isConnected) {
            bleGatt.disconnect()
            bleGatt.close()
        }
    }

    fun write(write_txt: String) {
        if (!isConnected) {
            Log.d(TAG, "not connected")
            return
        }

        val cmdCharacteristic = BluetoothUtils.findCommandCharacteristic(bleGatt)
        // disconnect if the characteristic is not found
        if (cmdCharacteristic == null) {
            Log.e(TAG, "Unable to find cmd characteristic")
            return
        }
        cmdCharacteristic.setValue(write_txt)

        val success: Boolean = bleGatt.writeCharacteristic(cmdCharacteristic)

        // check the result
        if (!success) {
            Log.e(TAG, "Failed to write command")
        }
    }

    @Synchronized
    fun readFromBluetooth(characteristic: BluetoothGattCharacteristic) {
        characteristic.getStringValue(0)
            .split(FileType.FINISH_CHAR)
            .forEach(this::read)
    }

    private fun read(msg: String) {
        val header = msg[0]
        val body = msg.substring(1)

        if (!files.containsKey(header)) {
            files[header] = File(FileType.startCharOf(header)).apply {
                Log.i(TAG, "start data reading")
                Log.i(TAG, "create file ${getFileName()}")
            }
        }

        val file: File = files[header]!!

        try {
            file.write(body)
            close(header) // TODO("TEMP")

            // TODO("call close")

        } catch (exception: RuntimeException) {
            exception.printStackTrace()

            files.remove(header)
            file.close()
        }
    }

    private fun close(header: Char) {
        files[header]?.let { file ->
            file.close()

            Log.i(TAG, "close file ${file.getFileName()}")
            Log.i(TAG, "file is created")
        }
        files.remove(header)
    }
}
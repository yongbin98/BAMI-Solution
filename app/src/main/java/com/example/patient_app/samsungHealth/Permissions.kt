package com.example.patient_app.samsungHealth

import android.Manifest

const val REQUEST_ALL_PERMISSION = 7

val PERMISSIONS = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.INTERNET,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.CHANGE_NETWORK_STATE
)
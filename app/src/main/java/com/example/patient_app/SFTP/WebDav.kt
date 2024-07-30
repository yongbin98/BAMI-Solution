package com.example.patient_app.SFTP

import android.util.Log
import com.example.patient_app.Activity.MainActivity_HR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.File

class WebDAV {
    private val TAG = "WebDAV"
    private val client = OkHttpClient()
    private val host = "http://163.180.145.126:5005/"
    private val userName = "webDAV_714"
    private val password = "bami123!"
    private val remoteFilePath = "BAMI3/webDAV_upload/"

    // Upload files to WebDAV server
    suspend fun upload(files:MutableList<java.io.File>) {
        val dir = getName(files)
        mkdir(dir)
        Log.i(TAG, "Uploading....")

        withContext(Dispatchers.IO) {
            files.forEach { file ->
                val mediaType = "application/octet-stream".toMediaTypeOrNull()
                val requestBody = file.asRequestBody(mediaType)
                val request = Request.Builder()
                    .url("$host$remoteFilePath${file.name}")
                    .put(requestBody)
                    .header("Authorization", Credentials.basic(userName, password))
                    .build()

                try {
                    val response: Response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        Log.i(TAG, "File ${file.name} uploaded successfully")
                    } else {
                        Log.e(TAG, "File ${file.name} upload failed: ${response.code} ${response.message}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error uploading file ${file.name}", e)
                }
            }
        }
    }

    // Create directory on WebDAV server
    private suspend fun mkdir(dir: String) = withContext(Dispatchers.IO) {
        val requestBody = "".toByteArray().toRequestBody("application/xml".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("$host$dir/")
            .header("Authorization", Credentials.basic(userName, password))
            .method("MKCOL", requestBody)
            .build()

        try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                Log.i(TAG, "Directory $dir created successfully")
            } else {
                Log.e(TAG, "Directory $dir creation failed: ${response.code} ${response.message}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating directory $dir", e)
        }
    }

    private fun getName(files: MutableList<File>): String {
        val dir = "BAMI Solution"
        val fullName = "ID_${MainActivity_HR.Patient_ID}"
        return "$dir/$fullName"
    }
}

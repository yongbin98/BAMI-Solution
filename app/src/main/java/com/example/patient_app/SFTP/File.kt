package com.example.patient_app.SFTP

import android.util.Log
import com.example.patient_app.Activity.MainActivity_HR
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

class File(
    private val fileType: FileType
) {

    companion object {
        private const val FILE_PATH = "/data/data/com.example.patient_app/files"
        var files = mutableListOf<File>()

        fun delete(){
            files.forEach {
                Log.i("FILE","${it.name}")
                it.delete()
            }
        }
    }

    private val file = File(FILE_PATH, createFileName())
    private val outputStream = BufferedOutputStream(FileOutputStream(file))
    private val writer = OutputStreamWriter(outputStream,"MS949")
//    private val printWriter = PrintWriter(file)

    private fun createFileName(): String {
        val dateFormat = SimpleDateFormat("MM-dd HH:mm:ss")
        val date = dateFormat.format(Date(System.currentTimeMillis()))

        return "${MainActivity_HR.Patient_ID}-${date}-${fileType.fileName}.csv"
    }

    fun getFileName(): String = file.name

    fun getFileLength(): Long = file.length()

    fun write(data: String) {
        writer.write(data)
//        printWriter.print(data) // TODO("buffer...?")
    }

    fun close() {
//        printWriter.close()
        writer.close()
        files.add(file)
    }
}
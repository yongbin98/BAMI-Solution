package com.example.patient_app.SFTP

import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

class File(
    private val fileType: FileType
) {

    companion object {
        private const val FILE_PATH = "/data/data/com.example.Patient_APP/files"
        var files = mutableListOf<File>()
    }

    private val file = File(FILE_PATH, createFileName())
    private val printWriter = PrintWriter(file)

    private fun createFileName(): String {
        val dateFormat = SimpleDateFormat("MM-dd HH:mm:ss")
        val date = dateFormat.format(Date(System.currentTimeMillis()))

        return "${date}-${fileType.fileName}.csv"
    }

    fun getFileName(): String = file.name

    fun getFileLength(): Long = file.length()

    fun write(data: String) {
        printWriter.print(data) // TODO("buffer...?")
    }

    fun close() {
        printWriter.close()
        files.add(file)
    }
}
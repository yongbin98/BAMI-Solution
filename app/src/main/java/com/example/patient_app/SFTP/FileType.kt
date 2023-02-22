package com.example.patient_app.SFTP

import java.lang.RuntimeException

enum class FileType(val startChar: Char, val fileName: String) {
    HEARTRATE('H', "HEARTRATE"),
    STEPCOUNT('C', "STEPCOUNT"),
    SURVEY('S', "SURVEY"),
    ID('I',"id");

    companion object {
        fun startCharOf(startChar: Char): FileType =
            values().find { it.startChar == startChar } ?: throw RuntimeException("Not Found File Type")
    }
}
package com.example.patient_app.SFTP

import android.util.Log
import com.example.patient_app.Activity.MainActivity_HR
import com.jcraft.jsch.*
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.net.UnknownHostException
import java.util.*


class SFTP {
    //SFTP 설정
    private val TAG = "SFTP"
    private var session : Session? = null
    private var channel : ChannelSftp? = null
    private val host = "aiwm.i234.me"
    private val userName = "bami01"
    private val password = "69706970"
    private val port = 7140
    private val privateKey = null
    companion object{
        var isFinished:Boolean = false
    }

    // 생성자
    init{
        var jSch : JSch = JSch()
        try {
            session = jSch.getSession(userName,host,port)
            session?.setPassword(password)
            var config : Properties = Properties()
            config["StrictHostKeyChecking"] = "no"
            session?.setConfig(config)
        } catch (e: FileNotFoundException){
            // 파일 존재 x
            e.printStackTrace()
        } catch (e: UnknownHostException){
            // 원격 호스트 유효 X
            e.printStackTrace()
        } catch (e : JSchException){
            // username 또는 password 일치 X
            e.printStackTrace()
        } catch (e: SftpException) {
            // FIFO 권한 X일때 처리
            e.printStackTrace()
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            session?.disconnect()
        }
    }

    // SFTP 서버 연결
    fun connect() {
        Log.i(TAG,"connecting....")
        session?.connect()

        channel = session?.openChannel("sftp") as ChannelSftp
        channel?.connect()
        Log.i(TAG,"connected")
    }

    // 디렉토리 생성
    private fun mkdir(dir:String)
    {
        val directory = dir.substring(0,dir.lastIndexOf('/'))
        val mkdirName = dir.substring(dir.lastIndexOf('/')+1)
        Log.i(TAG,"$directory , $mkdirName")
        try {
            channel?.cd(directory)
            channel?.mkdir(mkdirName)
        } catch (e: SftpException) {
            e.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    //파일 업로드
    fun upload(files:MutableList<java.io.File>) {
        val dir = getName(files)
        mkdir(dir)
        Log.i(TAG,"uploading....")
        var filein: FileInputStream? = null
        try {
            channel?.cd(dir)
            for (file in files)
            {
                Log.i(TAG,file.name)
                filein = FileInputStream(file)
                channel?.put(filein, file.name)
            }
            Log.i(TAG,"upload successfully!")
        } catch (e: SftpException) {
            //directory error
            e.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            try {
                filein?.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }

    private fun getName(files : MutableList<java.io.File>): String {
        val dir = "/BAMI Solution"
        val full_name = "ID_${MainActivity_HR.Patient_ID}"
        return "$dir/$full_name"
    }

    fun disconnect(){
        channel?.quit()
        session?.disconnect()
    }
}
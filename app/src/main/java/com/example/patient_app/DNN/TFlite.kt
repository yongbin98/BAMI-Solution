package com.example.patient_app.DNN

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TFlite (context: Context) {
    private var interpreter: Interpreter? = null

    init {
        interpreter = Interpreter(loadModelFile(context, "model.tflite"))
    }

    @Throws(IOException::class)
    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun runInference(inputA: Array<Array<FloatArray>>, inputB: Array<FloatArray>): FloatArray {
        val output = Array(1) { FloatArray(1) }
        interpreter?.runForMultipleInputsOutputs(arrayOf(inputA, inputB), mapOf(0 to output))

        // Assuming model output is between 0 and 1, scale it to be between 1 and 5

        return output[0].map { scaleToRange(it, 0f, 1f, 1f, 5f) }.toFloatArray()
    }

    private fun scaleToRange(value: Float, srcMin: Float, srcMax: Float, dstMin: Float, dstMax: Float): Float {
        return (value - srcMin) / (srcMax - srcMin) * (dstMax - dstMin) + dstMin
    }

}
package com.nothingwhisper

import com.facebook.react.bridge.*
import android.media.MediaRecorder
import java.io.File

class STTModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    private var recorder: MediaRecorder? = null
    private var outputFile: String = ""

    override fun getName() = "STTModule"

    @ReactMethod
    fun startRecording(promise: Promise) {
        val context = reactApplicationContext
        val file = File(context.cacheDir, "stt.wav")
        outputFile = file.absolutePath

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(outputFile)
            prepare()
            start()
        }

        promise.resolve("recording")
    }

    @ReactMethod
    fun stopRecording(promise: Promise) {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null

        promise.resolve(outputFile)
    }

    @ReactMethod
    fun transcribe(promise: Promise) {
        try {
            val cactus = CactusSpeechToText(reactApplicationContext)
            val result = cactus.transcribe(File(outputFile))
            promise.resolve(result.text)
        } catch (e: Exception) {
            promise.reject("ERR_STT", e)
        }
    }
}
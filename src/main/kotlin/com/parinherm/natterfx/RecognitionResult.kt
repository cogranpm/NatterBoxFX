package com.parinherm.natterfx

import java.io.ByteArrayOutputStream
import java.time.Instant

class RecognitionResult(
    val text: String?,
    val audioData: ArrayList<Pair<ByteArray, Int>>,
    val timeOf: Instant = Instant.now()
) {
    fun getCleanedText(): String{
        if(text != null){
            return text.trimStart().trimEnd()
        } else {
            return ""
        }
    }

    fun getAudioData(): Pair<ByteArray, Int> {
        val output = ByteArrayOutputStream()
        audioData.forEach {
            val (audio, length) = it
            output.write(audio, 0, length)
        }
        val audioData = output.toByteArray()
        return Pair(audioData, output.size())
    }
}

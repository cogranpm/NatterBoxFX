package com.parinherm.natterfx

import java.time.Instant

class RecognitionResult(
    val text: String,
    val audioData: ByteArray,
    val audioLength: Int,
    val timeOf: Instant = Instant.now()
) {
    fun getCleanedText(): String{
        return text.trimStart().trimEnd()
    }
}

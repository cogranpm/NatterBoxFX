package com.parinherm.natterfx

import java.time.Instant

data class RecognitionResult(
    val text: RecognizerText,
    val audioData: ByteArray,
    val audioLength: Int,
    val timeOf: Instant = Instant.now()
)

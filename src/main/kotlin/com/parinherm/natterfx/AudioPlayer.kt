package com.parinherm.natterfx

import javax.sound.sampled.*

object AudioPlayer {
    var speakers: SourceDataLine
    val format = AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000f, 16, 2, 4, 44100f, false)
    val dataLineInfo: DataLine.Info = DataLine.Info(SourceDataLine::class.java, format)
    init {
        speakers = AudioSystem.getLine(dataLineInfo) as SourceDataLine
    }

    fun open(){
        speakers.open(format)
        speakers.start()
    }

    fun play(audio: ByteArray, length: Int){
        speakers.write(audio, 0, length)
    }

    fun close(){
        speakers.drain()
        speakers.close()
    }
}
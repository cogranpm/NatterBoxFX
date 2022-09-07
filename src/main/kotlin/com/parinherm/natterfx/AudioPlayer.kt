package com.parinherm.natterfx

import javax.sound.sampled.*

object AudioPlayer {
    private var speakers: SourceDataLine
    private val format = AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000f, 16, 2, 4, 44100f, false)
    private val dataLineInfo: DataLine.Info = DataLine.Info(SourceDataLine::class.java, format)
    init {
        speakers = AudioSystem.getLine(dataLineInfo) as SourceDataLine
    }

    fun open(){
        speakers.open(format)
        speakers.start()
    }

    fun play(audio: ByteArray, length: Int){
        try {
            println("playing")
            println(audio)
            speakers.write(audio, 0, length)
        } catch (e: Exception){
           println(e.message)
        }
    }

    fun close(){
        speakers.drain()
        speakers.close()
    }
}
/****************************************************************
https://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
File wavFile = new File("E:/Test/RecordAudio.wav");
AudioInputStream ais = new AudioInputStream(line);
System.out.println("Start recording...");
// start recording
AudioSystem.write(ais, fileType, wavFile);

 ******************************************************************/

package com.parinherm.natterfx

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.vosk.LibVosk
import org.vosk.LogLevel
import org.vosk.Model
import org.vosk.Recognizer
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Paths
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.TargetDataLine

class AudioRecognizer() {


    val SPEAKERS_ON = false
    var microphone: TargetDataLine

    /*
   val sampleRate = 16000f
    val sampleSizeInBits = 8
    val channels = 2
    val bigEndian = false
    val frameSize = 4
    val frameRate = 44100f
     */

    val audioFormat = AudioFormat.Encoding.PCM_SIGNED
    val sampleRate = 60000f
    val sampleSizeInBits = 16
    val channels = 2
    val bigEndian = false
    val frameSize = 4
    val frameRate = 44100f

    val format = AudioFormat(audioFormat, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian)
    val info = DataLine.Info(TargetDataLine::class.java, format)
    val modelPath: String
    val jsonFormat = Json { isLenient = true }
    val CHUNK_SIZE = 1024

    init {
        LibVosk.setLogLevel(LogLevel.DEBUG)
        microphone = AudioSystem.getLine(info) as TargetDataLine
        val currentPath = Paths.get("").toAbsolutePath().normalize()
        val parentPath = currentPath.parent
        modelPath = "${parentPath.toString()}${File.separator}model"
        microphone.open(format)
        microphone.start()
    }

    fun run(): Flow<RecognitionResult> = flow {
        val audioQueue = arrayListOf<Pair<ByteArray, Int>>()
        try {
            Model(modelPath).use { model ->
                Recognizer(model, 120000f).use { recognizer ->

                    var numBytesRead: Int
                    //val out = ByteArrayOutputStream()
                    val b = ByteArray(4096)
                    while (true) {
                        var accepted = false
                        val available = microphone.available()
                        numBytesRead = microphone.read(b, 0, CHUNK_SIZE)
                        //numBytesRead = microphone.read(b, 0, available)
                        //out.write(b, 0, numBytesRead)
                        if (recognizer.acceptWaveForm(b, numBytesRead)) {
                            audioQueue.add(Pair(b.copyOf(), numBytesRead))
                            accepted = true
                            //emit(recognizer.result)
                        } else {
                            //System.out.println(recognizer.partialResult)
                            audioQueue.add(Pair(b.copyOf(), numBytesRead))
                        }
                        if (accepted) {
                            val data = jsonFormat.decodeFromString<RecognizerText>(recognizer.result)
                            val result = RecognitionResult(data.text, audioQueue)
                            emit(result)
                            audioQueue.clear()
                        }

                        if (SPEAKERS_ON) {
                            AudioPlayer.play(b, numBytesRead)
                            //AudioPlayer.play(out.toByteArray(), out.size())
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("we have stopped listening")
        } finally {
            cleanup()
        }
    }

    private fun cleanup() {
        microphone.stop()
        microphone.close()
    }

    /*
    suspend fun runSpeechCapture() {

        try {
            Model("D:\\shared\\Source\\kotlin\\resources\\vosk-model-small").use { model ->
                Recognizer(model, 120000f).use { recognizer ->
                    microphone.open(format)
                    microphone.start()
                    speakers.open(format)
                    speakers.start()
                    captureSpeech(microphone, recognizer, speakers)
                    System.out.println(recognizer.finalResult)
                }
            }
        } catch (e: Exception) {
            println("we have stopped listening")
        } finally {
            println("cleaning up")
            speakers.drain()
            speakers.close()
            microphone.close()
        }
    }

    suspend fun captureSpeech(microphone: TargetDataLine, recognizer: Recognizer, speakers: SourceDataLine) {
        val out = ByteArrayOutputStream()
        var numBytesRead: Int
        val CHUNK_SIZE = 1024
        var bytesRead = 0

        val b = ByteArray(4096)
        val maxBytes = 100000000
        while (true) {
            yield()
            numBytesRead = microphone.read(b, 0, CHUNK_SIZE)
            bytesRead += numBytesRead
            out.write(b, 0, numBytesRead)

            //this plays back what is read, useful for caching the audio for writing to file
            //speakers.write(b, 0, numBytesRead)
            if (recognizer.acceptWaveForm(b, numBytesRead)) {
                System.out.println(recognizer.result)
            } else {
                //System.out.println(recognizer.partialResult)
            }
        }
    }
     */

}


package com.parinherm.natterfx

import com.parinherm.natterfx.database.*
import javafx.application.Platform
import javafx.collections.FXCollections
//import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.paint.Color
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import kotlin.coroutines.CoroutineContext


object UI : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.JavaFx
}

object RecognizerScope : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO//Dispatchers.Default
}

class MainController {
    private lateinit var job: Job
    private lateinit var gc: GraphicsContext
    val recognizer = AudioRecognizer()
    //val allAudio: ArrayList<Pair<ByteArray, Int>> = arrayListOf()
    val recognitionList: ObservableList<String>

    init {

        recognitionList = FXCollections.observableArrayList()
    }

    suspend fun addItem(item: RecognitionResult) {
        Platform.runLater {
            recognitionList.add(item.text)
        }
    }

    fun shutdown() {

        UI.launch {
            job.cancelAndJoin()
        }

    }

    @FXML
    private fun initialize() {
        lstView.items = recognitionList
        gc = spectrum.graphicsContext2D
        gc.lineWidth = 10.0
        gc.fill = Color.ALICEBLUE
        DatabaseSession.open()
        gc.fill = Color.AQUA
        gc.fillRect(10.0, 10.0, 100.0, 100.0)
        job = recognizer.run().cancellable().onEach { value: RecognitionResult ->
            try {
                val (audioData, audioLength) = value.getAudioData()
                val cleanedText = value.getCleanedText()
                if(cleanedText.isNotEmpty()) {
                    QuizCommands.processInput(cleanedText, audioData, audioLength)
                    addItem(value)
                } else {
                    //this is audio data
                    val (peak, rms) = AudioConverter.calculatePeakAndRms(AudioConverter.encodeToSample(audioData, audioLength))
                    Platform.runLater {
                        gc.fill = Color.AZURE
                        gc.fillRect(0.0, 0.0, 400.0, 300.0)
                        gc.fill = Color.RED
                        //gc.fillOval(4.0, 4.0, peak * -1, rms * -1)
                        if(rms > 0.0 && peak > 0.0) {
                            println("${peak}${rms}")
                            gc.fillRect(10.0, 0.0,  peak * -100, rms * -100)
                        }
                    }
                }
                //allAudio.addAll(value.audioData)
            } catch (e: Exception) {
                println(e.message)
            }
        }.catch { e ->
            println("Caught $e")
        }
            .launchIn(RecognizerScope)
    }

    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private lateinit var lstView: ListView<String>

    @FXML
    private lateinit var spectrum: Canvas


    /*
    @FXML fun drawCanvas(event: ActionEvent){
        gc.fill = Color.AQUA
        gc.fillRect(10.0, 10.0, 100.0, 100.0)
    }

     */

    @FXML
    private fun onHelloButtonClick() {
        runBlocking { job.cancelAndJoin() }

       if(QuizCommands.entityList.isNotEmpty()){
           val lastQuiz = QuizCommands.entityList.last()
           val recognitions = RecognitionRepository.getByQuiz(lastQuiz)
           recognitions.forEach{
               println(it.tag)
               AudioPlayer.play(it.audio.bytes, it.length)
           }
       }
        welcomeText.text = "Playing Audio"

        /*
        val output = ByteArrayOutputStream()
        allAudio.forEach {
            val (audio, length) = it
            output.write(audio, 0, length)
        }
        val audioData = output.toByteArray()
        AudioPlayer.play(audioData, output.size())
         */


        /*
       if(recognitionList.isNotEmpty()){
            val lastAdded = RecognitionRepository.getMostRecent()
            if(lastAdded != null){
                println(lastAdded.text)
                println(lastAdded.length)
                println(lastAdded.ts)
                AudioPlayer.play(lastAdded.audio.bytes, lastAdded.length)
            }
        }
         */
    }
}

/*
        recognitionList.addListener(ListChangeListener<String>() {
            if(it.next()){
                if(it.wasAdded()){
                   // welcomeText.text = "added one"
                    //Platform.runLater { welcomeText.text = "added one" }
                }
            }
        })

 */

//RecognizerScope.coroutineContext.job.cancelAndJoin()
//Dispatchers.shutdown()

/*
runBlocking{
    job.cancelAndJoin()
}
 */
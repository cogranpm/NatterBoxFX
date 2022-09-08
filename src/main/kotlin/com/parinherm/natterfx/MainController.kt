package com.parinherm.natterfx

import com.parinherm.natterfx.database.*
import javafx.application.Platform
import javafx.collections.FXCollections
//import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ListView
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
    var job: Job
    val recognizer = AudioRecognizer()
    //val allAudio: ArrayList<Pair<ByteArray, Int>> = arrayListOf()
    val recognitionList: ObservableList<String>

    init {
        recognitionList = FXCollections.observableArrayList()
        DatabaseSession.open()

        job = recognizer.run().cancellable().onEach { value: RecognitionResult ->
            try {
                val (audioData, audioLength) = value.getAudioData()
                QuizCommands.processInput(value, audioData, audioLength)
                if(value.getCleanedText().isNotEmpty()){
                    addItem(value)
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
    }

    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private lateinit var lstView: ListView<String>

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
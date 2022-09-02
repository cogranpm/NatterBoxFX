package com.parinherm.natterfx

import javafx.application.Application
import javafx.collections.FXCollections.emptyObservableList
import javafx.collections.ObservableList
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx
import kotlin.coroutines.CoroutineContext

object  UI : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.JavaFx
}

object RecognizerScope: CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO//Dispatchers.Default
}

class HelloApplication : Application() {
    val recognizer = AudioRecognizer()
    lateinit var job: Job
    val recognitionList: ObservableList<RecognitionResult> = emptyObservableList()

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()

        //job = recognizerScope.launch(newSingleThreadContext("recognizer-loop")) {
        /************************************
         * UI Style
        job = UI.launch(Dispatchers.IO) {
            recognizer.run().cancellable().onEach { value: RecognitionResult ->
                println("we got one: ${value.text} Length: ${value.audioLength} Timestamp: ${value.timeOf}")
                //recognitionList.add(value)
            }.catch { e -> println("Caught $e")  }.collect {}
        }
        */

        /***********************************
         * launchIn style
         */
        job = recognizer.run().cancellable().onEach { value: RecognitionResult ->
            println("we got one: ${value.text} Length: ${value.audioLength} Timestamp: ${value.timeOf}")
            //recognitionList.add(value)
        }.catch { e ->
            println("Caught $e")
        }
            .launchIn(RecognizerScope)
        /************************************/
    }

    override fun stop() {
        super.stop()
        runBlocking {
            shutDown(this)
        }
    }

    suspend fun shutDown(scope: CoroutineScope){
        job.cancelAndJoin()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.javafx.JavaFx as Main

class HelloApplication : Application() {
    val recognizer = AudioRecognizer()
    val recognizerScope = GlobalScope
    lateinit var  job: Job
    val recognitionList: ObservableList<RecognitionResult> = emptyObservableList()

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()

        //job = recognizerScope.launch(newSingleThreadContext("recognizer-loop")) {
        job = recognizerScope.launch(Dispatchers.IO) {
            recognizer.run().cancellable().onEach { value: RecognitionResult ->
                println("we got one: ${value.text} Length: ${value.audioLength} Timestamp: ${value.timeOf}")
                //recognitionList.add(value)
            }.catch { e -> println("Caught $e")  }.collect {}
        }

    }

    override fun stop() {
        super.stop()
        runBlocking {
            job.cancelAndJoin()
        }
        //GlobalScope.launch(Dispatchers.Main) { job.cancelAndJoin() }
        //recognizerScope.cancel()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}
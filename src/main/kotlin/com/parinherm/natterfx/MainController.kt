package com.parinherm.natterfx

import com.parinherm.natterfx.database.DatabaseSession
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Label
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
    val recognitionList: ObservableList<RecognitionResult>
    var job: Job
    val recognizer = AudioRecognizer()

    init {
        recognitionList = FXCollections.observableArrayList()
        DatabaseSession.open()

        recognitionList.addListener(ListChangeListener<RecognitionResult>() {
            if(it.next()){
                if(it.wasAdded()){
                   // welcomeText.text = "added one"
                    //Platform.runLater { welcomeText.text = "added one" }
                }
            }
        })

        job = recognizer.run().cancellable().onEach { value: RecognitionResult ->
            /********************
             * every speech uttered comes here
             */
            //recognitionList.add(value)
            addItem(value)
        }.catch { e ->
            println("Caught $e")
        }
            .launchIn(RecognizerScope)

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
        /************************************/
    }

    suspend fun addItem(item: RecognitionResult) {
        //println("we got one: ${item.text.text} Length: ${item.audioLength} Timestamp: ${item.timeOf}")
        recognitionList.add(item)
        Platform.runLater { welcomeText.text = item.text}
        //welcomeText.text =
    }

    fun shutdown() {

        UI.launch {
           job.cancelAndJoin()
        }
        //RecognizerScope.coroutineContext.job.cancelAndJoin()
        //Dispatchers.shutdown()

        /*
        runBlocking{
            job.cancelAndJoin()
        }
         */
    }


    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Natter"
    }
}
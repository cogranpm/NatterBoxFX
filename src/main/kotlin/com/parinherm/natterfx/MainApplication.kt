package com.parinherm.natterfx

import com.parinherm.natterfx.database.EmbeddedDatabase
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage


class MainApplication() : Application() {
    override fun start(stage: Stage) {
        Preferences.databaseHost = "media-server"
        Preferences.databasePassword = ""
        Preferences.databaseUser = "sa"
        Preferences.databasePort = 3306
        Preferences.networkServer = false
        if(!Preferences.networkServer){
            EmbeddedDatabase.start()
        }
        val fxmlLoader = FXMLLoader(MainApplication::class.java.getResource("main-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        val controller = fxmlLoader.getController<MainController>()
        stage.title = "Natter"
        stage.scene = scene
        stage.setOnHidden { controller.shutdown() }
        stage.show()
    }

    override fun stop() {
        super.stop()
        if(!Preferences.networkServer){
            EmbeddedDatabase.stop()
        }
    }
}

fun main() {
   Application.launch(MainApplication::class.java )
}
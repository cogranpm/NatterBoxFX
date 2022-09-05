package com.parinherm.natterfx.database

import ch.vorburger.mariadb4j.DB
import ch.vorburger.mariadb4j.DBConfigurationBuilder
import com.parinherm.natterfx.Preferences
import java.io.File
import java.nio.file.Paths

object EmbeddedDatabase {
    val db: DB
    init {
        val currentPath = Paths.get("").toAbsolutePath().normalize()
        val parentPath = currentPath.parent
        val dataPath = "${parentPath}${File.separator}data"
        val dataDir = File(dataPath)
        dataDir.mkdirs()
        val configBuilder = DBConfigurationBuilder.newBuilder()
        configBuilder.setPort(Preferences.databasePort)
        configBuilder.setDataDir(dataPath)
        db = DB.newEmbeddedDB(configBuilder.build())
    }

    fun start(){
        db.start()
    }

    fun stop(){
        db.stop()
    }
}
package com.parinherm.natterfx.database

import com.parinherm.natterfx.Preferences
import java.io.File
import java.nio.file.Paths

object EmbeddedDatabase {
    val dataPath: String
    init {
        val currentPath = Paths.get("").toAbsolutePath().normalize()
        val parentPath = currentPath.parent
        dataPath = "${parentPath}${File.separator}data"
        val dataDir = File(dataPath)
        dataDir.mkdirs()
    }

    fun start(){
    }

    fun stop(){
    }
}
package com.parinherm.natterfx.database

import com.parinherm.natterfx.Preferences
import org.jetbrains.exposed.sql.Database

object DatabaseSession {
    val db by lazy {
        if(Preferences.networkServer){
            Database.connect("jdbc:mariadb://${Preferences.databaseHost}://${Preferences.databasePort}/NatterFX",
                driver="org.mariadb.jdbc.Driver",
                user=Preferences.databaseUser,
                password=Preferences.databasePassword
            )
        } else {
                Database.connect("jdbc:h2:${EmbeddedDatabase.dataPath}", "org.h2.Driver")
            }
        }
}
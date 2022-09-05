package com.parinherm.natterfx.database

import com.parinherm.natterfx.Preferences
import org.jetbrains.exposed.sql.Database

object DatabaseSession {
    val db by lazy {
        Database.connect("jdbc:mariadb://${Preferences.databaseHost}://${Preferences.databasePort}/NatterFX",
            driver="org.mariadb.jdbc.Driver",
            user=Preferences.databaseUser,
            password=Preferences.databasePassword
        )
    }
}
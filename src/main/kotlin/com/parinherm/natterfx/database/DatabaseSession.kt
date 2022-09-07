package com.parinherm.natterfx.database

import com.parinherm.natterfx.Preferences
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSession {
    val db by lazy {
        if(Preferences.networkServer){
            Database.connect("jdbc:mariadb://${Preferences.databaseHost}://${Preferences.databasePort}/natter",
                driver="org.mariadb.jdbc.Driver",
                user=Preferences.databaseUser,
                password=Preferences.databasePassword
            )
        } else {
            println(EmbeddedDatabase.dataPath)
                Database.connect("jdbc:h2:${EmbeddedDatabase.dataPath}", "org.h2.Driver", user = Preferences.databaseUser, password = Preferences.databasePassword)
            }
        }

    fun open(){
        if(db == null){
            return
        }
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.createMissingTablesAndColumns(QuizEntities, RecognitionEntities)
        }
    }

}

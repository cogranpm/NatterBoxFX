package com.parinherm.natterfx

object Preferences {
    var databaseHost: String
    var databasePort: String
    var databaseUser: String
    var databasePassword: String

    init {
        databaseHost = "localhost"
        databasePort = "3306"
        databaseUser = "user"
        databasePassword = "password"
    }

}
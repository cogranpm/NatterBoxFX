package com.parinherm.natterfx

import java.util.prefs.Preferences

const val DATABASE_HOST = "databaseHost"
const val DATABASE_PORT = "databasePort"
const val DATABASE_USER = "databaseUser"
const val DATABASE_PASSWORD = "databasePassword"
const val NETWORK_SERVER = "networkServer"

object Preferences {
    var databaseHost: String
    var databasePort: String
    var databaseUser: String
    var databasePassword: String
    var networkServer: Boolean

    val prefs = Preferences.userRoot().node("com/parinerm/natterfx")

    init {
        databaseHost = "localhost"
        databasePort = "3306"
        databaseUser = "user"
        databasePassword = "password"
        networkServer = true
    }

    fun load() {
        databaseHost = prefs.get(DATABASE_HOST, databaseHost)
        databasePort = prefs.get(DATABASE_PORT, databasePort)
        databaseUser = prefs.get(DATABASE_USER, databaseUser)
        databasePassword = prefs.get(DATABASE_PASSWORD, databasePassword)
        networkServer = prefs.getBoolean(NETWORK_SERVER, networkServer)
    }

    fun save() {
        prefs.put(DATABASE_HOST, databaseHost)
        prefs.put(DATABASE_PORT, databasePort)
        prefs.put(DATABASE_USER, databaseUser)
        prefs.put(DATABASE_PASSWORD, databasePassword)
        prefs.putBoolean(NETWORK_SERVER, networkServer)
    }

}
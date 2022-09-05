package com.parinherm.natterfx.database

import org.jetbrains.exposed.dao.id.UUIDTable

object RecognitionResults : UUIDTable() {
    val text = varchar("text", 4000)
}
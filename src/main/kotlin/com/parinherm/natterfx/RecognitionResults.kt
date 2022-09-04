package com.parinherm.natterfx

import org.jetbrains.exposed.dao.id.UUIDTable

object RecognitionResults : UUIDTable() {
    val text = varchar("text", 4000)
}
package com.parinherm.natterfx.database

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.util.*


object RecognitionEntities : UUIDTable(){
    val text = text("text")
    val audio = blob("audio")
    val ts = timestamp("ts")
}

class RecognitionEntity (id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RecognitionEntity>(RecognitionEntities)
    var text by RecognitionEntities.text
    var audio by RecognitionEntities.audio
    var ts by RecognitionEntities.ts
}

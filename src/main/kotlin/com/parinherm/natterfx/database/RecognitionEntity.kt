package com.parinherm.natterfx.database

import com.parinherm.natterfx.database.QuizEntities.defaultExpression
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp
import java.util.*


object RecognitionEntities : UUIDTable(){
    val text = text("text")
    val tag = varchar("tag", 25)
    //val audio: Column<ByteArray> =  binary("audio", Int.MAX_VALUE)
    val audio =  blob("audio")
    val length = integer("length")
    val ts = datetime("ts").defaultExpression(CurrentDateTime)
    val quizId = reference("quiz_id", QuizEntities)
}

class RecognitionEntity (id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RecognitionEntity>(RecognitionEntities)
    var text by RecognitionEntities.text
    var tag by RecognitionEntities.tag
    var audio by RecognitionEntities.audio
    var length by RecognitionEntities.length
    var ts by RecognitionEntities.ts
    var quiz by QuizEntity referencedOn RecognitionEntities.quizId
}

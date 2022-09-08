package com.parinherm.natterfx.database

import com.parinherm.natterfx.database.RecognitionEntities.quizId
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.UUID

object QuizEntities : UUIDTable(){
    val name = varchar("name", 255)
    val ts = datetime("ts").defaultExpression(CurrentDateTime)
}

class QuizEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<QuizEntity>(QuizEntities)
    var name by QuizEntities.name
    var ts by QuizEntities.ts
    val recognitions by RecognitionEntity referrersOn quizId
}


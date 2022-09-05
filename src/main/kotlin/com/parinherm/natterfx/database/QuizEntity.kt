package com.parinherm.natterfx.database

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object QuizEntities : UUIDTable(){
    val name = varchar("name", 255)
}

class QuizEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<QuizEntity>(QuizEntities)
    var name by QuizEntities.name
}

